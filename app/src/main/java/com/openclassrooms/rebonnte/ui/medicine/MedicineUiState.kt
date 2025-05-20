package com.openclassrooms.rebonnte.ui.medicine

import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User

/**
 * UI state representation for the Medicine screen.
 *
 * @property isLoading Indicates if the screen is currently loading data.
 * @property error Resource ID of an error message to display, or null if no error.
 * @property medicine List of medicines to display in the UI.
 * @property user The currently logged-in user, or null if no user is logged in.
 * @property successMessage Resource ID of a success message to display, or null if no message.
 */
data class MedicineUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val medicine: List<Medicine> = emptyList(),
    val user: User? = null,
    val successMessage: Int? = null,
)
