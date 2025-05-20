package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import javax.inject.Inject

/**
 * Use case for deleting a medicine by its name.
 *
 * @property repo The [MedicineRepositoryInterface] used to perform data operations.
 */
class DeleteMedicineByNameUseCase @Inject constructor(
    private val repo: MedicineRepositoryInterface
) {

    /**
     * Invokes the use case to delete a medicine with the specified name.
     *
     * @param name The name of the medicine to delete.
     * @return A [Result] indicating success or failure of the operation.
     */
    suspend operator fun invoke(name: String): Result<Unit> =
        repo.deleteMedicineByName(name)
}