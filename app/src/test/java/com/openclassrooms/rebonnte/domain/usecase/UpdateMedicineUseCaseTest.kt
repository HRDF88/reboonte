package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.UpdateMedicineUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateMedicineUseCaseTest {

    private val mockRepo = mockk<MedicineRepositoryInterface>()
    private val useCase = UpdateMedicineUseCase(mockRepo)

    @Test
    fun `invoke calls repository updateMedicine and returns success result`() = runBlocking {
        val medicine = Medicine(name = "yars revenge", stock = 20, nameAisle = "atari", histories = emptyList())
        val expectedResult = Result.success(Unit)

        coEvery { mockRepo.updateMedicine(medicine) } returns expectedResult

        val result = useCase.invoke(medicine)

        assertTrue(result.isSuccess)
    }
}
