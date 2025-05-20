package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import javax.inject.Inject

/**
 * Use case for adding a new medicine to the repository.
 *
 * @property repository The [MedicineRepositoryInterface] used to perform data operations.
 */
class AddMedicineUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {

    /**
     * Invokes the use case to add the specified medicine.
     *
     * @param medicine The [Medicine] to add.
     * @return A [Result] indicating success or failure of the operation.
     */
    suspend operator fun invoke(medicine: Medicine): Result<Unit> =
        repository.addMedicine(medicine)
}