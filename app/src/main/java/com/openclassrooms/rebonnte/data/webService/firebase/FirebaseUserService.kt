package com.openclassrooms.rebonnte.data.webService.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.data.webService.serviceInterface.UserApi
import com.openclassrooms.rebonnte.domain.mapper.toDomainUser
import com.openclassrooms.rebonnte.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseUserService : UserApi {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var signInDeferred: CompletableDeferred<FirebaseUser?>? = null

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("User")

    /**
     * Callback for handling the result of the sign-in intent.
     *
     * @param result The result from the Firebase Auth UI.
     */
    override fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        Log.d("AuthUI", "onSignInResult called with resultCode: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val user = auth.currentUser
            Log.d("AuthUI", "User signed in: ${user?.email}")
            signInDeferred?.complete(user)
        } else {
            Log.d("AuthUI", "Sign-in failed or cancelled")
            signInDeferred?.complete(null)
        }
        signInDeferred = null
    }

    /**
     * Launches the sign-in process using the given activity result launcher.
     *
     * @param launcher An [ActivityResultLauncher] used to launch the Firebase Auth UI.
     * @return The signed-in [User], or null if the sign-in was cancelled or failed.
     */
    override suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User? {
        if (signInDeferred != null && !signInDeferred!!.isCompleted) {
            throw IllegalStateException("Sign-in already in progress.")
        }

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.logo_firebase_ui)
            .setTheme(R.style.FirebaseUITheme)
            .build()

        signInDeferred = CompletableDeferred()
        launcher.launch(intent)

        val firebaseUser = signInDeferred?.await()
        return firebaseUser?.toDomainUser()
    }

    /**
     * Signs the current user out of Firebase.
     */
    override fun signOut() {
        auth.signOut()
    }

    /**
     * Returns the currently signed-in [User], or null if not signed in.
     */
    override fun getCurrentUser(): User? {
        return auth.currentUser?.toDomainUser()
    }

    /**
     * Checks whether a user is currently signed in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Loads and observes the current user's data from Firestore.
     *
     * @return A [Flow] that emits updates to the current user's data.
     */
    override fun loadUser(): Flow<User> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            close()
            return@callbackFlow
        }

        val listener = usersCollection.document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) {
                    close(error)
                    return@addSnapshotListener
                }

                val user = snapshot.toObject(User::class.java)
                if (user != null) trySend(user)
            }

        awaitClose { listener.remove() }
    }
}



