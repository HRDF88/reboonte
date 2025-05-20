package com.openclassrooms.rebonnte.ui.addMedicine

import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User

/**
 * Represents the UI state for adding a medicine.
 *
 * @property isLoading Indicates whether a loading operation is in progress.
 * @property error Holds the resource ID of an error message, or null if no error.
 * @property medicine The current [Medicine] data being added or edited, or null if none.
 * @property user The current [User] associated with the operation, or null if none.
 * @property aisle The list of available [Aisle] objects, defaulting to empty.
 * @property successMessage Holds the resource ID of a success message, or null if no success.
 */
data class AddMedicineUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val medicine: Medicine? = null,
    val user: User? = null,
    val aisle: List<Aisle> = emptyList(),
    val successMessage: Int? = null,
)


