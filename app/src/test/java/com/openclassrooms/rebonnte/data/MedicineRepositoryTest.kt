package com.openclassrooms.rebonnte.data

import com.openclassrooms.rebonnte.data.repository.MedicineRepository
import com.openclassrooms.rebonnte.data.webService.serviceInterface.MedicineApi
import com.openclassrooms.rebonnte.domain.model.Medicine
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MedicineRepositoryTest {

    private lateinit var medicineApi: MedicineApi
    private lateinit var repository: MedicineRepository

    private val testMedicine = Medicine(
        name = "Doliprane", stock = 10, nameAisle = "testing", histories =
        emptyList()
    )

    @Before
    fun setUp() {
        medicineApi = mockk()
        repository = MedicineRepository(medicineApi)
    }

    @Test
    fun `addMedicine returns success`() = runBlocking {
        coEvery { medicineApi.addMedicine(testMedicine) } returns Result.success(Unit)

        val result = repository.addMedicine(testMedicine)

        coVerify { medicineApi.addMedicine(testMedicine) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `addMedicine returns failure`() = runBlocking {
        val exception = Exception("API Error")
        coEvery { medicineApi.addMedicine(testMedicine) } returns Result.failure(exception)

        val result = repository.addMedicine(testMedicine)

        coVerify { medicineApi.addMedicine(testMedicine) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getAllMedicines returns expected flow`() = runTest {
        val expected = listOf(testMedicine)
        every { medicineApi.getAllMedicines() } returns flowOf(expected)

        val result = repository.getAllMedicines()

        result.collect {
            assertEquals(expected, it)
        }

        verify { medicineApi.getAllMedicines() }
    }

    @Test
    fun `getAllMedicinesSortedByName returns sorted flow`() = runTest {
        val expected = listOf(testMedicine)
        every { medicineApi.getAllMedicinesSortedByName() } returns flowOf(expected)

        val result = repository.getAllMedicinesSortedByName()

        result.collect {
            assertEquals(expected, it)
        }

        verify { medicineApi.getAllMedicinesSortedByName() }
    }

    @Test
    fun `getAllMedicinesSortedByStock returns sorted flow`() = runTest {
        val expected = listOf(testMedicine)
        every { medicineApi.getAllMedicinesSortedByStock() } returns flowOf(expected)

        val result = repository.getAllMedicinesSortedByStock()

        result.collect {
            assertEquals(expected, it)
        }

        verify { medicineApi.getAllMedicinesSortedByStock() }
    }

    @Test
    fun `searchMedicinesByName returns matching flow`() = runTest {
        val expected = listOf(testMedicine)
        every { medicineApi.searchMedicinesByName("Doli") } returns flowOf(expected)

        val result = repository.searchMedicinesByName("Doli")

        result.collect {
            assertEquals(expected, it)
        }

        verify { medicineApi.searchMedicinesByName("Doli") }
    }

    @Test
    fun `updateMedicine returns success`() = runBlocking {
        coEvery { medicineApi.updateMedicine(testMedicine) } returns Result.success(Unit)

        val result = repository.updateMedicine(testMedicine)

        coVerify { medicineApi.updateMedicine(testMedicine) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `updateMedicine returns failure`() = runBlocking {
        val exception = Exception("Update failed")
        coEvery { medicineApi.updateMedicine(testMedicine) } returns Result.failure(exception)

        val result = repository.updateMedicine(testMedicine)

        coVerify { medicineApi.updateMedicine(testMedicine) }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteMedicineByName returns success`() = runBlocking {
        coEvery { medicineApi.deleteMedicineByName("Doliprane") } returns Result.success(Unit)

        val result = repository.deleteMedicineByName("Doliprane")

        coVerify { medicineApi.deleteMedicineByName("Doliprane") }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `deleteMedicineByName returns failure`() = runBlocking {
        val exception = Exception("Delete failed")
        coEvery { medicineApi.deleteMedicineByName("Doliprane") } returns Result.failure(exception)

        val result = repository.deleteMedicineByName("Doliprane")

        coVerify { medicineApi.deleteMedicineByName("Doliprane") }
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
