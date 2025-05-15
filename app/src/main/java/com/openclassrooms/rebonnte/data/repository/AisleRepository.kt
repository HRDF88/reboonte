package com.openclassrooms.rebonnte.data.repository

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.data.webService.serviceInterface.AisleApi
import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AisleRepository @Inject constructor(private val aisleApi: AisleApi) :
    AisleRepositoryInterface {

    override suspend fun addAisle(aisle: Aisle): Result<Unit> {
        return aisleApi.addAisle(aisle)
    }

    override fun getAllAisles(): Flow<List<Aisle>> {
        return aisleApi.getAllAisles()
    }

    override suspend fun deleteAisleByName(name: String): Result<Unit> {
        return aisleApi.deleteAisleByName(name)
    }
}