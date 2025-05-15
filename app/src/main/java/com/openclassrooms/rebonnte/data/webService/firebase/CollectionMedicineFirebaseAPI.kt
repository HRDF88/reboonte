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

class CollectionMedicineFirebaseAPI @Inject constructor(
    private val firestore: FirebaseFirestore
) : MedicineApi {

    private val medicineCollection = firestore.collection("Medicines")

    override suspend fun addMedicine(medicine: Medicine): Result<Unit> = try {
        medicineCollection
            .add(medicine.toFirestoreMap())
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

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

    override fun searchMedicinesByName(query: String): Flow<List<Medicine>> = callbackFlow {
        val lowercaseQuery = query.lowercase()
        val end = lowercaseQuery + '\uf8ff'

        val registration = medicineCollection
            .orderBy("name_lowercase")
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
