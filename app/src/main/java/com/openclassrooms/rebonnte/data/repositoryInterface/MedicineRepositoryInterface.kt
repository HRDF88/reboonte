package com.openclassrooms.rebonnte.data.repositoryInterface

import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the medicine repository, defining operations for managing medicines.
 * Serves as an abstraction layer between the application and the data source.
 */
interface MedicineRepositoryInterface {

    /**
     * Adds a new medicine to the repository.
     *
     * @param medicine The [Medicine] object to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addMedicine(medicine: Medicine): Result<Unit>

    /**
     * Retrieves all medicines as a reactive stream.
     *
     * @return A [Flow] emitting lists of [Medicine] objects.
     */
    fun getAllMedicines(): Flow<List<Medicine>>

    /**
     * Updates an existing medicine in the repository.
     * The update is typically based on the medicine's name.
     *
     * @param medicine The [Medicine] object with updated data.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun updateMedicine(medicine: Medicine): Result<Unit>

    /**
     * Retrieves all medicines sorted alphabetically by name.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted by name.
     */
    fun getAllMedicinesSortedByName(): Flow<List<Medicine>>

    /**
     * Retrieves all medicines sorted by their stock levels.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted by stock quantity.
     */
    fun getAllMedicinesSortedByStock(): Flow<List<Medicine>>

    /**
     * Searches for medicines whose names match the given query string.
     *
     * @param query The query string to match against medicine names.
     * @return A [Flow] emitting lists of matching [Medicine] objects.
     */
    fun searchMedicinesByName(query: String): Flow<List<Medicine>>

    /**
     * Deletes medicine entries that match the specified name.
     *
     * @param name The name of the medicine(s) to delete.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun deleteMedicineByName(name: String): Result<Unit>
}
