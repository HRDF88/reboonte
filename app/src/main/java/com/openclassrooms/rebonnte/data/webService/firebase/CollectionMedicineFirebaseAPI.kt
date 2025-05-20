package com.openclassrooms.rebonnte.data.webService.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.openclassrooms.rebonnte.data.webService.serviceInterface.MedicineApi
import com.openclassrooms.rebonnte.domain.mapper.toFirestoreMap
import com.openclassrooms.rebonnte.domain.mapper.toMedicine
import com.openclassrooms.rebonnte.domain.model.Medicine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of [MedicineApi] that interacts with Firebase Firestore
 * to perform CRUD operations on the "Medicines" collection.
 *
 * @property firestore The FirebaseFirestore instance used to access Firestore.
 */
class CollectionMedicineFirebaseAPI @Inject constructor(
    private val firestore: FirebaseFirestore
) : MedicineApi {

    private val medicineCollection = firestore.collection("Medicines")

    /**
     * Adds a new medicine document to the "Medicines" Firestore collection.
     *
     * @param medicine The medicine object to add.
     * @return A [Result] indicating success or failure of the operation.
     */
    override suspend fun addMedicine(medicine: Medicine): Result<Unit> = try {
        medicineCollection
            .add(medicine.toFirestoreMap())
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Returns a [Flow] emitting the list of all medicines in real-time.
     * Emits updates whenever the "Medicines" collection changes.
     *
     * @return A flow emitting lists of [Medicine].
     */
    override fun getAllMedicines(): Flow<List<Medicine>> = callbackFlow {
        val registration = medicineCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val list = snapshot
                ?.documents
                ?.mapNotNull { it.toMedicine() }
                ?: emptyList()
            trySend(list)
        }
        awaitClose { registration.remove() }
    }

    /**
     * Returns a [Flow] emitting the list of all medicines sorted by their "name" field.
     *
     * @return A flow emitting lists of [Medicine] sorted by name.
     */
    override fun getAllMedicinesSortedByName(): Flow<List<Medicine>> = callbackFlow {
        val registration = medicineCollection
            .orderBy("name")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val list = snapshot
                    ?.documents
                    ?.mapNotNull { it.toMedicine() }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    /**
     * Returns a [Flow] emitting the list of all medicines sorted by their "stock" field.
     *
     * @return A flow emitting lists of [Medicine] sorted by stock.
     */
    override fun getAllMedicinesSortedByStock(): Flow<List<Medicine>> = callbackFlow {
        val registration = medicineCollection
            .orderBy("stock")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val list = snapshot
                    ?.documents
                    ?.mapNotNull { it.toMedicine() }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    /**
     * Returns a [Flow] emitting the list of all medicines sorted by their "name" field.
     *
     * @return A flow emitting lists of [Medicine] sorted by name.
     */
    override fun searchMedicinesByName(query: String): Flow<List<Medicine>> = callbackFlow {
        val lowercaseQuery = query.lowercase()
        val end = lowercaseQuery + '\uf8ff'

        val registration = medicineCollection
            .orderBy("name")
            .startAt(lowercaseQuery)
            .endAt(end)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val list = snapshot
                    ?.documents
                    ?.mapNotNull { it.toMedicine() }
                    ?: emptyList()
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    /**
     * Updates existing medicines with the same name by merging the provided medicine data.
     *
     * @param medicine The medicine object with updated data.
     * @return A [Result] indicating success or failure of the operation.
     */
    override suspend fun updateMedicine(medicine: Medicine): Result<Unit> = try {
        val snap = medicineCollection
            .whereEqualTo("name", medicine.name)
            .get()
            .await()

        snap.documents.forEach { doc ->
            doc.reference
                .set(medicine.toFirestoreMap(), SetOptions.merge())
                .await()
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Deletes all medicines from the "Medicines" collection that match the given name.
     *
     * @param name The name of the medicine(s) to delete.
     * @return A [Result] indicating success or failure of the operation.
     */
    override suspend fun deleteMedicineByName(name: String): Result<Unit> = try {
        val snap = medicineCollection
            .whereEqualTo("name", name)
            .get()
            .await()

        snap.documents.forEach { doc ->
            doc.reference.delete().await()
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
