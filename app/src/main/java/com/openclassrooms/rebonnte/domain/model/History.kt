package com.openclassrooms.rebonnte.domain.model

/**
 * Represents a record of a specific event or change related to a medicine.
 *
 * @property medicineName The name of the medicine involved in the history entry.
 * @property userId The identifier of the user who performed the action or is associated with the entry.
 * @property date The date the action was performed or the event occurred, typically stored as a String
 * @property details Additional details about the action or event.
 */
class History(
    var medicineName: String,
    var userId: String,
    var date: String,
    var details: String
)
