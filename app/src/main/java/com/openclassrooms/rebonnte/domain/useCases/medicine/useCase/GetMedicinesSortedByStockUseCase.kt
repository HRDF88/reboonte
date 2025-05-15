package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicinesSortedByStockUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {
    operator fun invoke(): Flow<List<Medicine>> =
        repository.getAllMedicinesSortedByStock()
}