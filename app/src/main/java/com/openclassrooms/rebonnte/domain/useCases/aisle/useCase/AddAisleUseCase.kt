package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import javax.inject.Inject

/**
 * Use case for adding a new aisle to the repository.
 *
 * @property repository The [AisleRepositoryInterface] used to perform data operations.
 */
class AddAisleUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {

    /**
     * Invokes the use case to add the specified aisle.
     *
     * @param aisle The [Aisle] to add.
     * @return A [Result] indicating success or failure of the operation.
     */
    open suspend operator fun invoke(aisle: Aisle): Result<Unit> =
        repository.addAisle(aisle)
}