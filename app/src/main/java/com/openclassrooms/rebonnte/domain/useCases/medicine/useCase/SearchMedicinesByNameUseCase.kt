package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMedicinesByNameUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {
    operator fun invoke(query: String): Flow<List<Medicine>> =
        repository.searchMedicinesByName(query)
}