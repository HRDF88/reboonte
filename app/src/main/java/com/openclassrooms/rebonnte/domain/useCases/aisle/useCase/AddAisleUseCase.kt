package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import javax.inject.Inject

class AddAisleUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {
    suspend operator fun invoke(aisle: Aisle): Result<Unit> =
        repository.addAisle(aisle)
}