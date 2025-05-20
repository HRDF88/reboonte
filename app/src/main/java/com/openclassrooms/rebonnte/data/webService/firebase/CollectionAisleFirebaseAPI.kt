package com.openclassrooms.rebonnte.data.webService.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.openclassrooms.rebonnte.data.webService.serviceInterface.AisleApi
import com.openclassrooms.rebonnte.domain.mapper.toAisle
import com.openclassrooms.rebonnte.domain.mapper.toFirestoreMap
import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of [AisleApi] that uses Firebase Firestore
 * to perform CRUD operations on the "Aisle" collection.
 *
 * @property firestore The FirebaseFirestore instance used to access Firestore.
 */
class CollectionAisleFirebaseAPI @Inject constructor(
    private val firestore: FirebaseFirestore
) : AisleApi {

    private val aisleCollection = firestore.collection("Aisle")

    /**
     * Adds a new aisle to the Firestore "Aisle" collection.
     *
     * @param aisle The aisle object to add.
     * @return A [Result] indicating success or failure of the operation.
     */
    override suspend fun addAisle(aisle: Aisle): Result<Unit> = try {
        aisleCollection.add(aisle.toFirestoreMap()).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Retrieves all aisles from the Firestore "Aisle" collection as a flow.
     * Emits the current list of aisles and updates whenever the collection changes.
     *
     * @return A [Flow] emitting lists of [Aisle].
     */
    override fun getAllAisles(): Flow<List<Aisle>> = callbackFlow {
        val listenerRegistration = aisleCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val aisles = snapshot?.documents?.mapNotNull { it.toAisle() } ?: emptyList()
            trySend(aisles)
        }

        awaitClose { listenerRegistration.remove() }
    }


    /**
     * Deletes all aisles matching the specified name from the Firestore "Aisle" collection.
     *
     * @param name The name of the aisle(s) to delete.
     * @return A [Result] indicating success or failure of the operation.
     */
    override suspend fun deleteAisleByName(name: String): Result<Unit> = try {
        val snapshot = aisleCollection.whereEqualTo("name", name).get().await()
        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
