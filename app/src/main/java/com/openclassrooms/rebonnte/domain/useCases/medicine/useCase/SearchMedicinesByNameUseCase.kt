package com.openclassrooms.rebonnte.domain.useCases.medicine.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for searching medicines by their name.
 *
 * @property repository The [MedicineRepositoryInterface] used to perform the search.
 */
class SearchMedicinesByNameUseCase @Inject constructor(
    private val repository: MedicineRepositoryInterface
) {

    /**
     * Invokes the use case to search medicines matching the given query string.
     *
     * @param query The search string to match medicine names.
     * @return A [Flow] emitting a list of [Medicine] objects matching the search query.
     */
    operator fun invoke(query: String): Flow<List<Medicine>> =
        repository.searchMedicinesByName(query)
}