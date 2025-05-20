package com.openclassrooms.rebonnte.ui.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for user authentication state and actions.
 *
 * It manages the current signed-in user and provides methods to start sign-in,
 * handle sign-in results, and sign out the user.
 *
 * @property userUseCases Use cases related to user authentication and management.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    private val _user = MutableStateFlow<User?>(userUseCases.getCurrentUser())
    val user: StateFlow<User?> = _user.asStateFlow()

    /**
     * Initiates the sign-in flow using the provided [ActivityResultLauncher].
     *
     * @param launcher The launcher to start the sign-in intent.
     */
    fun startSignIn(launcher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            val signedInUser = userUseCases.signIn(launcher)
            _user.value = signedInUser
        }
    }

    /**
     * Handles the result returned from the sign-in activity.
     *
     * Should be called from the activity's onActivityResult or equivalent callback.
     *
     * @param result The Firebase authentication result to process.
     */
    fun handleSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        userUseCases.onSignInResult(result)
        _user.value = userUseCases.getCurrentUser()

    }

    /**
     * Signs out the current user and clears the user state.
     */
    fun signOut() {
        userUseCases.signOut()
        _user.value = null
    }

}