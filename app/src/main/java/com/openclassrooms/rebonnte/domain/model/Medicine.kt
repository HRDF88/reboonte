package com.openclassrooms.rebonnte.domain.model

/**
 * Represents a medicine entity with its basic details and history records.
 *
 * @property name The name of the medicine.
 * @property stock The current stock quantity of the medicine.
 * @property nameAisle The name of the aisle where the medicine is stored.
 * @property histories A list of [History] records associated with this medicine.
 */
data class Medicine(
    var name: String,
    var stock: Int,
    var nameAisle: String,
    var histories: List<History>
)
