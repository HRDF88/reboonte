package com.openclassrooms.rebonnte.data.repositoryInterface

import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow

interface AisleRepositoryInterface {

    suspend fun addAisle(aisle: Aisle): Result<Unit>

    fun getAllAisles(): Flow<List<Aisle>>

    suspend fun deleteAisleByName(name: String): Result<Unit>
}