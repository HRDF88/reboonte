package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.SearchMedicinesByNameUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchMedicinesByNameUseCaseTest {

    private val mockRepository = mockk<MedicineRepositoryInterface>()
    private val useCase = SearchMedicinesByNameUseCase(mockRepository)

    @Test
    fun `invoke returns flow of medicines matching the query from repository`() = runBlocking {
        val query = "tetris"
        val searchResult = listOf(
            Medicine(name = "tetris", stock = 10, nameAisle = "gameboy", histories = emptyList()),
            Medicine(name = "tetris 2000", stock = 5, nameAisle = "remake", histories = emptyList())
        )
        val flowSearchResult: Flow<List<Medicine>> = flowOf(searchResult)

        every { mockRepository.searchMedicinesByName(query) } returns flowSearchResult

        val result = useCase.invoke(query)

        result.collect { emittedMedicines ->
            assertEquals(searchResult, emittedMedicines)
        }
    }
}
