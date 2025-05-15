package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import javax.inject.Inject

class AddMedicineUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {
    suspend operator fun invoke(medicine: Medicine): Result<Unit> =
        repository.addMedicine(medicine)
}