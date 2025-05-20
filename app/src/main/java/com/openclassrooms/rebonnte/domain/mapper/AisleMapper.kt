package com.openclassrooms.rebonnte.domain.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.openclassrooms.rebonnte.domain.model.Aisle

/**
 * Converts a [DocumentSnapshot] to an [Aisle] object.
 *
 * @receiver The [DocumentSnapshot] retrieved from Firestore.
 * @return An [Aisle] object if the conversion is successful, or `null` if the data is missing or invalid.
 */
fun DocumentSnapshot.toAisle(): Aisle? {
    val data = this.data ?: return null
    return Aisle(name = data["name"] as? String ?: "")
}

/**
 * Converts an [Aisle] object to a Firestore-compatible [Map] for storage.
 *
 * @receiver The [Aisle] instance to be converted.
 * @return A [Map] representation of the aisle suitable for Firestore storage.
 */
fun Aisle.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "name" to name
    )
}