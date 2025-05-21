package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.AddMedicineUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AddMedicineUseCaseTest {

    private val mockRepository = mockk<MedicineRepositoryInterface>()
    private val useCase = AddMedicineUseCase(mockRepository)

    @Test
    fun `invoke calls repository addMedicine and returns success result`() = runBlocking {
        val medicine = Medicine(name = "smecta", stock = 10, nameAisle = "testing", histories = emptyList())
        val expectedResult = Result.success(Unit)

        coEvery { mockRepository.addMedicine(medicine) } returns expectedResult

        val result = useCase.invoke(medicine)

        assertEquals(expectedResult, result)
    }
}
