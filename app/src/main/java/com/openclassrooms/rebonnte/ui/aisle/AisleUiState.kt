package com.openclassrooms.rebonnte.ui.aisle

import com.openclassrooms.rebonnte.domain.model.Aisle

/**
 * Represents the UI state for the aisle screen.
 *
 * @property isLoading Indicates whether data is currently being loaded.
 * @property error Resource ID of an error message to display, or null if no error.
 * @property aisle The list of aisles currently loaded.
 */
data class AisleUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val aisle: List<Aisle> = emptyList()
)
