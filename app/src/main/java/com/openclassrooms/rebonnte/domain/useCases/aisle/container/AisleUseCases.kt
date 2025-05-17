package com.openclassrooms.rebonnte.domain.useCases.aisle.container

import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.AddAisleUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.DeleteAisleByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.GetAllAislesUseCase
import javax.inject.Inject

data class AisleUseCases @Inject constructor(
    val addAisleUseCase: AddAisleUseCase,
    val deleteAisleByNameUseCase: DeleteAisleByNameUseCase,
    val getAllAislesUseCase: GetAllAislesUseCase
)
