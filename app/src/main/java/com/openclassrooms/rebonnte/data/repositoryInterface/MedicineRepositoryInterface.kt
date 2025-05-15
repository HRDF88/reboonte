package com.openclassrooms.rebonnte.data.repositoryInterface

import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineRepositoryInterface {

    suspend fun addMedicine(medicine: Medicine): Result<Unit>

    fun getAllMedicines(): Flow<List<Medicine>>

    suspend fun updateMedicine(medicine: Medicine): Result<Unit>

    fun getAllMedicinesSortedByName(): Flow<List<Medicine>>

    fun getAllMedicinesSortedByStock(): Flow<List<Medicine>>

    fun searchMedicinesByName(query: String): Flow<List<Medicine>>

    suspend fun deleteMedicineByName(name: String): Result<Unit>
}
