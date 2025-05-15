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

@HiltViewModel
class AuthViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    private val _user = MutableStateFlow<User?>(userUseCases.getCurrentUser())
    val user: StateFlow<User?> = _user.asStateFlow()

    fun startSignIn(launcher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            val signedInUser = userUseCases.signIn(launcher)
            _user.value = signedInUser
        }
    }

    /** Appelée depuis onActivityResult pour propager le résultat */
    fun handleSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        userUseCases.onSignInResult(result)
        // récupère à nouveau l’utilisateur courant
        _user.value = userUseCases.getCurrentUser()

    }

    /** Déconnexion */
    fun signOut() {
        userUseCases.signOut()
        _user.value = null
    }

}