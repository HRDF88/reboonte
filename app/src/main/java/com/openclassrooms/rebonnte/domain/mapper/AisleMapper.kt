package com.openclassrooms.rebonnte.domain.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.openclassrooms.rebonnte.domain.model.Aisle

fun DocumentSnapshot.toAisle(): Aisle? {
    val data = this.data ?: return null
    return Aisle(name = data["name"] as? String ?: "")
}


fun Aisle.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "name" to name
    )
}