package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.DeleteMedicineByNameUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DeleteMedicineByNameUseCaseTest {

    private val mockRepo = mockk<MedicineRepositoryInterface>()
    private val useCase = DeleteMedicineByNameUseCase(mockRepo)

    @Test
    fun `invoke calls repo deleteMedicineByName and returns success result`() = runBlocking {
        val medicineName = "smecta"
        val expectedResult = Result.success(Unit)

        coEvery { mockRepo.deleteMedicineByName(medicineName) } returns expectedResult

        val result = useCase.invoke(medicineName)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke returns failure result when deletion fails`() = runBlocking {
        val medicineName = "atari"
        val expectedException = Exception("Delete failed")
        val expectedResult = Result.failure<Unit>(expectedException)

        coEvery { mockRepo.deleteMedicineByName(medicineName) } returns expectedResult

        val result = useCase.invoke(medicineName)

        assertEquals(expectedResult, result)
    }
}
