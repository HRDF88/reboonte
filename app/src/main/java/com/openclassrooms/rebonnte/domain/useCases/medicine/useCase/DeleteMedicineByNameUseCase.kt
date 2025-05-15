package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import javax.inject.Inject

class DeleteMedicineByNameUseCase @Inject constructor(
    private val repo: MedicineRepositoryInterface
) {
    suspend operator fun invoke(name: String): Result<Unit> =
        repo.deleteMedicineByName(name)
}