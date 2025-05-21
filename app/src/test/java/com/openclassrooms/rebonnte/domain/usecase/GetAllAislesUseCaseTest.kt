package com.openclassrooms.rebonnte.domain.usecase


import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.domain.useCases.aisle.useCase.GetAllAislesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class GetAllAislesUseCaseTest {

    private val mockRepository = mockk<AisleRepositoryInterface>()
    private val useCase = GetAllAislesUseCase(mockRepository)

    @Test
    fun `invoke returns flow of aisle list from repository`() = runBlocking {
        val aisles = listOf(Aisle("testing"), Aisle("testing2"))
        every { mockRepository.getAllAisles() } returns flowOf(aisles)

        val result = useCase().toList()

        assertEquals(1, result.size)
        assertEquals(aisles, result[0])
    }
}