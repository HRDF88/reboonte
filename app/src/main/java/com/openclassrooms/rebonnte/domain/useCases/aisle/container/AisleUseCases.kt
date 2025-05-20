package com.openclassrooms.rebonnte.domain.useCases.aisle.container

import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.AddAisleUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.DeleteAisleByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.GetAllAislesUseCase
import javax.inject.Inject

/**
 * Aggregates the use cases related to aisle management.
 *
 * @property addAisleUseCase Use case for adding a new aisle.
 * @property deleteAisleByNameUseCase Use case for deleting an aisle by its name.
 * @property getAllAislesUseCase Use case for retrieving all aisles.
 */
data class AisleUseCases @Inject constructor(
    val addAisleUseCase: AddAisleUseCase,
    val deleteAisleByNameUseCase: DeleteAisleByNameUseCase,
    val getAllAislesUseCase: GetAllAislesUseCase
)
