package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByNameUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMedicinesSortedByNameUseCaseTest {

    private val mockRepository = mockk<MedicineRepositoryInterface>()
    private val useCase = GetMedicinesSortedByNameUseCase(mockRepository)

    @Test
    fun `invoke returns flow of medicines sorted by name from repository`() = runBlocking {
        val medicinesSorted = listOf(
            Medicine(name = "missile command", stock = 10, nameAisle = "atari", histories = emptyList()),
            Medicine(name = "medievil", stock = 20, nameAisle = "sony", histories = emptyList())
        )
        val flowSorted: Flow<List<Medicine>> = flowOf(medicinesSorted)

        every { mockRepository.getAllMedicinesSortedByName() } returns flowSorted

        val result = useCase.invoke()

        result.collect { emittedMedicines ->
            assertEquals(medicinesSorted, emittedMedicines)
        }
    }
}
