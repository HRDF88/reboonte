package com.openclassrooms.rebonnte.ui.medicine

import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User

data class MedicineUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val medicine: List<Medicine> = emptyList(),
    val user : User? = null,
)
