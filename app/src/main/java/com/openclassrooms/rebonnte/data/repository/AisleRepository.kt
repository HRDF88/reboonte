package com.openclassrooms.rebonnte.data.repository

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.serviceInterface.AisleApi
import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository implementation for managing aisles.
 * Delegates data operations to the provided [AisleApi] implementation.
 *
 * @property aisleApi The API used to perform aisle-related data operations.
 */
class AisleRepository @Inject constructor(private val aisleApi: AisleApi) :
    AisleRepositoryInterface {

    /**
     * Adds a new aisle by delegating the operation to [AisleApi].
     *
     * @param aisle The [Aisle] object to add.
     * @return A [Result] indicating the success or failure of the operation.
     */
    override suspend fun addAisle(aisle: Aisle): Result<Unit> {
        return aisleApi.addAisle(aisle)
    }

    /**
     * Retrieves all aisles as a reactive stream by delegating to [AisleApi].
     *
     * @return A [Flow] emitting lists of [Aisle] objects.
     */
    override fun getAllAisles(): Flow<List<Aisle>> {
        return aisleApi.getAllAisles()
    }

    /**
     * Deletes aisles by name by delegating the operation to [AisleApi].
     *
     * @param name The name of the aisle(s) to delete.
     * @return A [Result] indicating the success or failure of the operation.
     */
    override suspend fun deleteAisleByName(name: String): Result<Unit> {
        return aisleApi.deleteAisleByName(name)
    }
}