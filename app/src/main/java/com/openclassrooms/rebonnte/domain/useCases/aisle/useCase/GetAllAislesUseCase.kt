package com.openclassrooms.rebonnte.domain.useCases.aisle.useCase

import com.openclassrooms.rebonnte.data.repositoryInterface.AisleRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving all aisles.
 *
 * @property repository The [AisleRepositoryInterface] used to fetch aisle data.
 */
class GetAllAislesUseCase @Inject constructor(
    private val repository: AisleRepositoryInterface
) {

    /**
     * Invokes the use case to get a flow of all aisles.
     *
     * @return A [Flow] emitting a list of [Aisle] objects.
     */
    open operator fun invoke(): Flow<List<Aisle>> =
        repository.getAllAisles()
}