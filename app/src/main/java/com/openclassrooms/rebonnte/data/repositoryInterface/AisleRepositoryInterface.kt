package com.openclassrooms.rebonnte.data.repositoryInterface

import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the aisle repository, defining methods for managing aisles.
 * Acts as an abstraction over the data layer, allowing different implementations.
 */
interface AisleRepositoryInterface {

    /**
     * Adds a new aisle to the repository.
     *
     * @param aisle The [Aisle] object to be added.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun addAisle(aisle: Aisle): Result<Unit>

    /**
     * Retrieves all aisles as a reactive stream.
     *
     * @return A [Flow] emitting lists of [Aisle] objects.
     */
    fun getAllAisles(): Flow<List<Aisle>>

    /**
     * Deletes all aisles that match the given name.
     *
     * @param name The name of the aisle(s) to delete.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend fun deleteAisleByName(name: String): Result<Unit>
}