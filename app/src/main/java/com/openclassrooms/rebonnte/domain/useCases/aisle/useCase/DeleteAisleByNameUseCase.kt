package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import javax.inject.Inject

class DeleteAisleByNameUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {
    suspend operator fun invoke(name: String): Result<Unit> =
        repository.deleteAisleByName(name)
}