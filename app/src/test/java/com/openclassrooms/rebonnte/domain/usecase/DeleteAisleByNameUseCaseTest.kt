package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.DeleteAisleByNameUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class DeleteAisleByNameUseCaseTest {

    private val mockRepository = mockk<AisleRepositoryInterface>()
    private val useCase = DeleteAisleByNameUseCase(mockRepository)

    @Test
    fun `invoke returns success when repository succeeds`() = runBlocking {
        val aisleName = "testing"
        val expected = Result.success(Unit)

        coEvery { mockRepository.deleteAisleByName(aisleName) } returns expected

        val result = useCase(aisleName)

        assertEquals(expected, result)
        coVerify { mockRepository.deleteAisleByName(aisleName) }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runBlocking {
        val aisleName = "testing"
        val error = Exception("Deletion error")
        val expected = Result.failure<Unit>(error)

        coEvery { mockRepository.deleteAisleByName(aisleName) } returns expected

        val result = useCase(aisleName)

        assertEquals(expected, result)
        coVerify { mockRepository.deleteAisleByName(aisleName) }
    }
}
