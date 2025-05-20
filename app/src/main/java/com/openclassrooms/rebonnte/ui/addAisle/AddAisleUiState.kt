package com.openclassrooms.rebonnte.ui.addAisle

import com.openclassrooms.rebonnte.domain.model.Aisle

/**
 * UI state data class representing the state of adding an aisle.
 *
 * @property isLoading Indicates if the add operation is currently in progress.
 * @property error Resource ID of an error message to display, or null if no error.
 * @property aisle The [Aisle] object being added or null if none.
 * @property successMessage Resource ID of a success message to display, or null if none.
 */
data class AddAisleUiState(
    val isLoading: Boolean = false,
    var error: Int? = null,
    val aisle: Aisle? = null,
    val successMessage: Int? = null,

    )
