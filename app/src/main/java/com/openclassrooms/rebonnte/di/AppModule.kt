package com.openclassrooms.rebonnte.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.openclassrooms.rebonnte.data.repository.AisleRepository
import com.openclassrooms.rebonnte.data.repository.MedicineRepository
import com.openclassrooms.rebonnte.data.repository.UserRepository
import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.firebase.CollectionAisleFirebaseAPI
import com.openclassrooms.rebonnte.data.webService.firebase.CollectionMedicineFirebaseAPI
import com.openclassrooms.rebonnte.data.webService.firebase.FirebaseUserService
import com.openclassrooms.rebonnte.data.webService.serviceInterface.AisleApi
import com.openclassrooms.rebonnte.data.webService.serviceInterface.MedicineApi
import com.openclassrooms.rebonnte.data.webService.serviceInterface.UserApi
import com.openclassrooms.rebonnte.domain.useCases.aisle.container.AisleUseCases
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.AddAisleUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.DeleteAisleByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.GetAllAislesUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.container.MedicineUseCases
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.AddMedicineUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.DeleteMedicineByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetAllMedicinesUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByStockUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.SearchMedicinesByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.UpdateMedicineUseCase
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

    @Provides
    @Singleton
    fun provideAisleApi(): AisleApi {
        return CollectionAisleFirebaseAPI(Firebase.firestore)
    }

    @Provides
    fun provideAisleRepository(aisleApi: AisleApi): AisleRepositoryInterface {
        return AisleRepository(aisleApi)
    }

    @Provides
    fun provideAisleUseCases(repository: AisleRepositoryInterface): AisleUseCases {
        return AisleUseCases(
            addAisleUseCase = AddAisleUseCase(repository),
            deleteAisleByNameUseCase = DeleteAisleByNameUseCase(repository),
            getAllAislesUseCase = GetAllAislesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMedicineApi(): MedicineApi {
        return CollectionMedicineFirebaseAPI(Firebase.firestore)
    }

    @Provides
    fun provideMedicineRepository(medicineApi: MedicineApi): MedicineRepositoryInterface {
        return MedicineRepository(medicineApi)
    }

    @Provides
    fun provideMedicineUseCases(
        repository: MedicineRepositoryInterface
    ): MedicineUseCases {
        return MedicineUseCases(
            addMedicine = AddMedicineUseCase(repository),
            getAllMedicines = GetAllMedicinesUseCase(repository),
            getMedicinesSortedByName = GetMedicinesSortedByNameUseCase(repository),
            getMedicinesSortedByStock = GetMedicinesSortedByStockUseCase(repository),
            searchMedicinesByName = SearchMedicinesByNameUseCase(repository),
            updateMedicine = UpdateMedicineUseCase(repository),
            deleteMedicineByName = DeleteMedicineByNameUseCase(repository)
        )
    }
}