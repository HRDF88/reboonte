package com.openclassrooms.rebonnte.data.repositoryInterface

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines user-related operations, including authentication,
 * user session management, and notification preferences.
 */
interface UserRepositoryInterface {

    /**
     * Handles the result of the Firebase authentication flow.
     *
     * @param result The result returned by Firebase Authentication UI.
     */
    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult)

    /**
     * Launches the sign-in process and suspends until a result is available.
     *
     * @param launcher The [ActivityResultLauncher] used to start the sign-in intent.
     * @return The signed-in [User], or `null` if sign-in failed or was cancelled.
     * @throws IllegalStateException if a sign-in process is already in progress.
     */
    suspend fun signIn(launcher: ActivityResultLauncher<Intent>): User?

    /**
     * Signs the current user out of the application.
     */
    fun signOut()

    /**
     * Retrieves the currently signed-in user, if any.
     *
     * @return The current [User], or `null` if no user is signed in.
     */
    fun getCurrentUser(): User?

    /**
     * Checks if a user is currently logged in.
     *
     * @return `true` if a user is logged in, `false` otherwise.
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Loads the user profile as a [Flow].
     *
     * @return A [Flow] emitting the [User] profile.
     */
    fun loadUser(): Flow<User>

}