package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAislesUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {
    operator fun invoke(): Flow<List<Aisle>> =
        repository.getAllAisles()
}