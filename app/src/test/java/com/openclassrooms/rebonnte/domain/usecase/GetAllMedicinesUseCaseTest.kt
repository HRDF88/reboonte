package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetAllMedicinesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAllMedicinesUseCaseTest {

    private val mockRepository = mockk<MedicineRepositoryInterface>()
    private val useCase = GetAllMedicinesUseCase(mockRepository)

    @Test
    fun `invoke returns flow of medicine list from repository`() = runBlocking {
        val medicines = listOf(
            Medicine(name = "adventure", stock = 10, nameAisle = "atari", histories = emptyList()),
            Medicine(name = "yars revenge", stock = 20, nameAisle = "atari", histories = emptyList())
        )
        val flowMedicines: Flow<List<Medicine>> = flowOf(medicines)

        every { mockRepository.getAllMedicines() } returns flowMedicines

        val result = useCase.invoke()

        result.collect { emittedMedicines ->
            assertEquals(medicines, emittedMedicines)
        }
    }
}
