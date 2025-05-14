package com.openclassrooms.rebonnte.data.webService.serviceInterface

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for user authentication and profile management operations.
 */
interface UserApi {

    /**
     * Handles the result of a Firebase Authentication UI sign-in attempt.
     *
     * @param result The result from the FirebaseAuthUIAuthenticationResult.
     */
    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult)

    /**
     * Initiates the sign-in process using the provided launcher.
     *
     * @param launcher The [ActivityResultLauncher] used to launch the sign-in intent.
     * @return The signed-in [User] if successful, or null if sign-in failed or was canceled.
     */
    suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User?

    /**
     * Signs out the currently authenticated user.
     */
    fun signOut()

    /**
     * Returns the currently signed-in user.
     *
     * @return The current [User], or null if no user is signed in.
     */
    fun getCurrentUser(): User?

    /**
     * Checks if a user is currently signed in.
     *
     * @return True if a user is signed in, false otherwise.
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Continuously loads the current user's profile and emits updates.
     *
     * @return A [Flow] that emits the [User] whenever their profile is updated.
     */
    fun loadUser(): Flow<User>

}
