package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving all medicines sorted by their stock quantity.
 *
 * @property repository The [MedicineRepositoryInterface] used to fetch medicine data.
 */
class GetMedicinesSortedByStockUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {

    /**
     * Invokes the use case to get a flow of medicines sorted by stock.
     *
     * @return A [Flow] emitting a list of [Medicine] objects sorted by stock quantity.
     */
    operator fun invoke(): Flow<List<Medicine>> =
        repository.getAllMedicinesSortedByStock()
}