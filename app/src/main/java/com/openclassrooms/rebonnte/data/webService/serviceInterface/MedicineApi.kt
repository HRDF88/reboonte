package com.openclassrooms.rebonnte.data.webService.serviceInterface

import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining operations for managing medicines in a data source.
 */
interface MedicineApi {

    /**
     * Adds a new medicine to the data source.
     *
     * @param medicine The [Medicine] object to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addMedicine(medicine: Medicine): Result<Unit>

    /**
     * Retrieves all medicines from the data source as a reactive stream.
     *
     * @return A [Flow] emitting lists of [Medicine] objects.
     */
    fun getAllMedicines(): Flow<List<Medicine>>

    /**
     * Searches medicines in the data source by name using a prefix query.
     *
     * @param query The name or prefix to search for.
     * @return A [Flow] emitting lists of [Medicine] objects that match the query.
     */
    fun searchMedicinesByName(query: String): Flow<List<Medicine>>

    /**
     * Retrieves all medicines sorted by name.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted alphabetically.
     */
    fun getAllMedicinesSortedByName(): Flow<List<Medicine>>

    /**
     * Retrieves all medicines sorted by stock quantity.
     *
     * @return A [Flow] emitting lists of [Medicine] objects sorted by their stock level.
     */
    fun getAllMedicinesSortedByStock(): Flow<List<Medicine>>

    /**
     * Updates the information of an existing medicine in the data source.
     * The update is based on the medicine's name.
     *
     * @param medicine The [Medicine] object with updated information.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun updateMedicine(medicine: Medicine): Result<Unit>

    /**
     * Deletes all medicines from the data source that match the given name.
     *
     * @param name The name of the medicine(s) to delete.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun deleteMedicineByName(name: String): Result<Unit>
}