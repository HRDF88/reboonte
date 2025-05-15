package com.openclassrooms.rebonnte.domain.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.openclassrooms.rebonnte.domain.model.History
import com.openclassrooms.rebonnte.domain.model.Medicine

fun DocumentSnapshot.toHistory(): History? {
    val data = this.data ?: return null
    return History(
        medicineName = data["medicineName"] as? String ?: "",
        userId       = data["userId"]       as? String ?: "",
        date         = data["date"]         as? String ?: "",
        details      = data["details"]      as? String ?: ""
    )
}

fun DocumentSnapshot.toMedicine(): Medicine? {
    val data = this.data ?: return null


    @Suppress("UNCHECKED_CAST")
    val rawHistories = data["histories"] as? List<Map<String, Any>> ?: emptyList()

    val histories = rawHistories.mapNotNull { map ->
        History(
            medicineName = map["medicineName"] as? String ?: return@mapNotNull null,
            userId       = map["userId"]       as? String ?: return@mapNotNull null,
            date         = map["date"]         as? String ?: return@mapNotNull null,
            details      = map["details"]      as? String ?: return@mapNotNull null
        )
    }

    return Medicine(
        name      = data["name"]      as? String ?: "",
        stock     = (data["stock"]    as? Long)?.toInt() ?: 0,
        nameAisle = data["nameAisle"] as? String ?: "",
        histories = histories
    )
}



fun History.toFirestoreMap(): Map<String, Any> = mapOf(
    "medicineName" to medicineName,
    "userId"       to userId,
    "date"         to date,
    "details"      to details
)

fun Medicine.toFirestoreMap(): Map<String, Any> = mapOf(
    "name"      to name,
    "stock"     to stock,
    "nameAisle" to nameAisle,
    "histories" to histories.map { it.toFirestoreMap() }
)