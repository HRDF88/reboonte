package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import javax.inject.Inject

/**
 * Use case for updating an existing medicine.
 *
 * @property repo The [MedicineRepositoryInterface] used to perform the update.
 */
class UpdateMedicineUseCase @Inject constructor(
    private val repo: MedicineRepositoryInterface
) {

    /**
     * Invokes the use case to update the given medicine.
     *
     * @param medicine The [Medicine] instance with updated data.
     * @return A [Result] indicating success or failure of the update operation.
     */
    suspend operator fun invoke(medicine: Medicine): Result<Unit> =
        repo.updateMedicine(medicine)
}