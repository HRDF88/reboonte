package com.openclassrooms.rebonnte.data.webService.serviceInterface

import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining operations for managing aisles.
 */
interface AisleApi {

    /**
     * Adds a new aisle to the data source.
     *
     * @param aisle The [Aisle] object to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addAisle(aisle: Aisle): Result<Unit>

    /**
     * Retrieves all aisles from the data source as a reactive stream.
     *
     * @return A [Flow] emitting lists of [Aisle] objects.
     */
    fun getAllAisles(): Flow<List<Aisle>>

    /**
     * Deletes all aisles that match the given name from the data source.
     *
     * @param name The name of the aisle(s) to delete.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun deleteAisleByName(name: String): Result<Unit>
}