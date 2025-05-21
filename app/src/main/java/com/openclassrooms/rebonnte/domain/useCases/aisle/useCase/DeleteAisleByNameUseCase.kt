package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import javax.inject.Inject

/**
 * Use case for deleting an aisle by its name.
 *
 * @property repository The [AisleRepositoryInterface] used to perform data operations.
 */
class DeleteAisleByNameUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {

    /**
     * Invokes the use case to delete an aisle by the specified name.
     *
     * @param name The name of the aisle to delete.
     * @return A [Result] indicating success or failure of the operation.
     */
    open suspend operator fun invoke(name: String): Result<Unit> =
        repository.deleteAisleByName(name)
}