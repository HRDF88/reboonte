package com.openclassrooms.rebonnte.ui.addAisle

import com.openclassrooms.rebonnte.domain.model.Aisle

data class AddAisleUiState(
    val isLoading: Boolean = false,
    var error: Int? = null,
    val aisle: Aisle? = null

)
