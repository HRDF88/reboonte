package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByStockUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMedicinesSortedByStockUseCaseTest {

    private val mockRepository = mockk<MedicineRepositoryInterface>()
    private val useCase = GetMedicinesSortedByStockUseCase(mockRepository)

    @Test
    fun `invoke returns flow of medicines sorted by stock from repository`() = runBlocking {
        val medicinesSortedByStock = listOf(
            Medicine(name = "yars revenge", stock = 5, nameAisle = "atari", histories = emptyList()),
            Medicine(name = "death stranding", stock = 15, nameAisle = "kojima", histories = emptyList())
        )
        val flowSortedByStock: Flow<List<Medicine>> = flowOf(medicinesSortedByStock)

        every { mockRepository.getAllMedicinesSortedByStock() } returns flowSortedByStock

        val result = useCase.invoke()

        result.collect { emittedMedicines ->
            assertEquals(medicinesSortedByStock, emittedMedicines)
        }
    }
}
