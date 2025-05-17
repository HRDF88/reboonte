package com.openclassrooms.rebonnte.ui.aisle

import com.openclassrooms.rebonnte.domain.model.Aisle
import kotlinx.coroutines.flow.Flow

data class AisleUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val aisle: List<Aisle> = emptyList()
)
