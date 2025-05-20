package com.openclassrooms.rebonnte.data.repository

import com.openclassrooms.rebonnte.data.repositoryInterface.MedicineRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.serviceInterface.MedicineApi
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository implementation for managing medicines.
 * Delegates all data operations to the provided [MedicineApi] implementation.
 *
 * @property medicineApi The API used to perform medicine-related data operations.
 */
class MedicineRepository @Inject constructor(
    private val medicineApi: MedicineApi
) : MedicineRepositoryInterface {

    /**
     * Adds a new medicine by delegating to [MedicineApi].
     *
     * @param medicine The [Medicine] object to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    override suspend fun addMedicine(medicine: Medicine): Result<Unit> =
        medicineApi.addMedicine(medicine)

    /**
     * Retrieves all medicines as a reactive stream by delegating to [MedicineApi].
     *
     * @return A [Flow] emitting lists of [Medicine] objects.
     */
    override fun getAllMedicines(): Flow<List<Medicine>> =
        medicineApi.getAllMedicines()

    /**
     * Retrieves all medicines sorted alphabetically by name.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted by name.
     */
    override fun getAllMedicinesSortedByName(): Flow<List<Medicine>> =
        medicineApi.getAllMedicinesSortedByName()

    /**
     * Retrieves all medicines sorted by stock quantity.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted by stock.
     */
    override fun getAllMedicinesSortedByStock(): Flow<List<Medicine>> =
        medicineApi.getAllMedicinesSortedByStock()

    /**
     * Searches for medicines that match the given name query.
     *
     * @param query The string to match against medicine names.
     * @return A [Flow] emitting lists of [Medicine] objects matching the query.
     */
    override fun searchMedicinesByName(query: String): Flow<List<Medicine>> =
        medicineApi.searchMedicinesByName(query)

    /**
     * Updates an existing medicine's data by delegating to [MedicineApi].
     *
     * @param medicine The [Medicine] object with updated data.
     * @return A [Result] indicating the success or failure of the update operation.
     */
    override suspend fun updateMedicine(medicine: Medicine): Result<Unit> =
        medicineApi.updateMedicine(medicine)

    /**
     * Deletes a medicine by its name by delegating to [MedicineApi].
     *
     * @param name The name of the medicine to delete.
     * @return A [Result] indicating the success or failure of the deletion operation.
     */
    override suspend fun deleteMedicineByName(name: String): Result<Unit> =
        medicineApi.deleteMedicineByName(name)
}
