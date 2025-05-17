package com.openclassrooms.rebonnte.ui.medicine

import com.openclassrooms.rebonnte.domain.model.Medicine

data class MedicineUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val medicine: List<Medicine> = emptyList()
)
