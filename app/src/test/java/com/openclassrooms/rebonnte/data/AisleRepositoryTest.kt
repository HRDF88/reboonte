package com.openclassrooms.rebonnte.data

import com.openclassrooms.rebonnte.data.repository.AisleRepository
import com.openclassrooms.rebonnte.data.webService.serviceInterface.AisleApi
import com.openclassrooms.rebonnte.domain.model.Aisle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AisleRepositoryTest {

    private lateinit var aisleApi: AisleApi
    private lateinit var repository: AisleRepository

    private val testAisle = Aisle(name = "testing")

    @Before
    fun setUp() {
        aisleApi = mockk()
        repository = AisleRepository(aisleApi)
    }

    @Test
    fun `addAisle returns success`() = runBlocking {
        coEvery { aisleApi.addAisle(testAisle) } returns Result.success(Unit)

        val result = repository.addAisle(testAisle)

        coVerify { aisleApi.addAisle(testAisle) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `addAisle returns failure`() = runBlocking {
        val exception = Exception("API error")
        coEvery { aisleApi.addAisle(testAisle) } returns Result.failure(exception)

        val result = repository.addAisle(testAisle)

        coVerify { aisleApi.addAisle(testAisle) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getAllAisles returns expected flow`() = runTest {
        val expected = listOf(testAisle)
        every { aisleApi.getAllAisles() } returns flowOf(expected)

        val result = repository.getAllAisles()

        result.collect { aisles ->
            assertEquals(expected, aisles)
        }

        verify { aisleApi.getAllAisles() }
    }

    @Test
    fun `deleteAisleByName returns success`() = runBlocking {
        coEvery { aisleApi.deleteAisleByName("testing") } returns Result.success(Unit)

        val result = repository.deleteAisleByName("testing")

        coVerify { aisleApi.deleteAisleByName("testing") }
        assertTrue(result.isSuccess)
    }
}