package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.AddAisleUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class AddAisleUseCaseTest {

    private val mockRepository = mockk<AisleRepositoryInterface>()
    private val useCase = AddAisleUseCase(mockRepository)

    @Test
    fun `invoke returns success when repository succeeds`() = runBlocking {
        val aisle = Aisle(name = "testing")
        val expected = Result.success(Unit)

        coEvery { mockRepository.addAisle(aisle) } returns expected

        val result = useCase(aisle)

        assertEquals(expected, result)
        coVerify { mockRepository.addAisle(aisle) }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runBlocking {
        val aisle = Aisle(name = "testing")
        val error = Exception("Database error")
        val expected = Result.failure<Unit>(error)

        coEvery { mockRepository.addAisle(aisle) } returns expected

        val result = useCase(aisle)

        assertEquals(expected, result)
        coVerify { mockRepository.addAisle(aisle) }
    }
}
