package com.openclassrooms.rebonnte.domain.useCases.user.container

import com.openclassrooms.rebonnte.domain.useCases.user.useCase.GetCurrentUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.IsUserLoggedInUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.LoadUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.OnSignInResultUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignInUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignOutUserUseCase
import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val onSignInResult: OnSignInResultUseCase,
    val signIn: SignInUserUseCase,
    val signOut: SignOutUserUseCase,
    val getCurrentUser: GetCurrentUserUseCase,
    val isUserLoggedIn: IsUserLoggedInUseCase,
    val loadUser: LoadUserUseCase
)