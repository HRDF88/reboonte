package com.openclassrooms.rebonnte.data.repository

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.serviceInterface.UserApi
import com.openclassrooms.rebonnte.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
* Implementation of [UserRepositoryInterface] that delegates user-related operations to [UserApi].
*
* This repository handles authentication, user data retrieval.
*
* @property userApi The API service responsible for user operations.
*/
class UserRepository @Inject constructor(
    private val userApi: UserApi
) : UserRepositoryInterface {


    private var signInDeferred: CompletableDeferred<User?>? = null

    /**
     * Handles the result of the Firebase sign-in process.
     *
     * @param result The authentication result returned from Firebase UI.
     */
    override fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val user = userApi.getCurrentUser()
            signInDeferred?.complete(user)
        } else {
            signInDeferred?.complete(null)
        }
    }

    /**
     * Initiates the Firebase sign-in flow.
     *
     * @param launcher The [ActivityResultLauncher] used to start the sign-in intent.
     * @return The signed-in [User] or null if sign-in failed.
     * @throws IllegalStateException if a sign-in operation is already in progress.
     */
    override suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User? {
        if (signInDeferred != null && !signInDeferred!!.isCompleted) {
            throw IllegalStateException("Sign-in already in progress.")
        }

        signInDeferred = CompletableDeferred()

        userApi.signIn(launcher)

        return signInDeferred?.await()
    }


    /**
     * Signs out the current user.
     */
    override fun signOut() {
        userApi.signOut()
    }

    /**
     * Returns the currently authenticated user, if any.
     *
     * @return The current [User] or null if no user is logged in.
     */
    override fun getCurrentUser(): User? {
        return userApi.getCurrentUser()
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    override fun isUserLoggedIn(): Boolean {
        return userApi.isUserLoggedIn()
    }

    /**
     * Loads the current user's data as a [Flow].
     *
     * @return A [Flow] emitting the [User] data.
     */
    override fun loadUser(): Flow<User> {
        return userApi.loadUser()
    }
}