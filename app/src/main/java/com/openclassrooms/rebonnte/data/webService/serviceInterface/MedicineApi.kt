package com.openclassrooms.rebonnte.data.webService.serviceInterface

import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineApi {

    suspend fun addMedicine(medicine: Medicine): Result<Unit>

    fun getAllMedicines(): Flow<List<Medicine>>

    fun searchMedicinesByName(query: String): Flow<List<Medicine>>

    fun getAllMedicinesSortedByName(): Flow<List<Medicine>>

    fun getAllMedicinesSortedByStock(): Flow<List<Medicine>>

    suspend fun updateMedicine(medicine: Medicine): Result<Unit>

    suspend fun deleteMedicineByName(name: String): Result<Unit>
}