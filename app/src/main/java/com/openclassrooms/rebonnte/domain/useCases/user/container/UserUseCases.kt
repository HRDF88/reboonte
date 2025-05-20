package com.openclassrooms.rebonnte.domain.useCases.user.container

import com.openclassrooms.rebonnte.domain.useCases.user.useCase.GetCurrentUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.IsUserLoggedInUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.LoadUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.OnSignInResultUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignInUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignOutUserUseCase
import javax.inject.Inject

/**
 * Aggregates all user-related use cases for easier injection and management.
 *
 * @property onSignInResult Use case to handle the result of a sign-in operation.
 * @property signIn Use case to sign in a user.
 * @property signOut Use case to sign out the current user.
 * @property getCurrentUser Use case to get the currently signed-in user.
 * @property isUserLoggedIn Use case to check if a user is currently logged in.
 * @property loadUser Use case to load user data from storage or network.
 */
data class UserUseCases @Inject constructor(
    val onSignInResult: OnSignInResultUseCase,
    val signIn: SignInUserUseCase,
    val signOut: SignOutUserUseCase,
    val getCurrentUser: GetCurrentUserUseCase,
    val isUserLoggedIn: IsUserLoggedInUseCase,
    val loadUser: LoadUserUseCase
)