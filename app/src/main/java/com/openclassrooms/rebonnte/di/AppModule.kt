package com.openclassrooms.rebonnte.di

import com.openclassrooms.rebonnte.data.repository.UserRepository
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.firebase.FirebaseUserService
import com.openclassrooms.rebonnte.data.webService.serviceInterface.UserApi
import com.openclassrooms.rebonnte.domain.useCases.user.container.UserUseCases
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.GetCurrentUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.IsUserLoggedInUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.LoadUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.OnSignInResultUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignInUserUseCase
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignOutUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing application dependencies.
 * This module defines how the application's dependencies, such as database, DAOs, and repositories,
 * are provided to the application components.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi(): UserApi {
        return FirebaseUserService()
    }

    @Provides
    fun provideUserRepository(userApi: UserApi): UserRepositoryInterface {
        return UserRepository(userApi)
    }

    @Provides
    fun provideUserUseCases(repository: UserRepositoryInterface): UserUseCases {
        return UserUseCases(
            onSignInResult = OnSignInResultUseCase(repository),
            signIn = SignInUserUseCase(repository),
            signOut = SignOutUserUseCase(repository),
            getCurrentUser = GetCurrentUserUseCase(repository),
            isUserLoggedIn = IsUserLoggedInUseCase(repository),
            loadUser = LoadUserUseCase(repository)
        )
    }
}