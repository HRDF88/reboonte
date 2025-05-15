package com.openclassrooms.rebonnte.data.repository

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.serviceInterface.MedicineApi
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val medicineApi: MedicineApi
) : MedicineRepositoryInterface {

    override suspend fun addMedicine(medicine: Medicine): Result<Unit> =
        medicineApi.addMedicine(medicine)

    override fun getAllMedicines(): Flow<List<Medicine>> =
        medicineApi.getAllMedicines()

    override fun getAllMedicinesSortedByName(): Flow<List<Medicine>> =
        medicineApi.getAllMedicinesSortedByName()

    override fun getAllMedicinesSortedByStock(): Flow<List<Medicine>> =
        medicineApi.getAllMedicinesSortedByStock()

    override fun searchMedicinesByName(query: String): Flow<List<Medicine>> =
        medicineApi.searchMedicinesByName(query)

    override suspend fun updateMedicine(medicine: Medicine): Result<Unit> =
        medicineApi.updateMedicine(medicine)

    override suspend fun deleteMedicineByName(name: String): Result<Unit> =
        medicineApi.deleteMedicineByName(name)
}
