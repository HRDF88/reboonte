package com.openclassrooms.rebonnte.ui.addMedicine

import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User

data class AddMedicineUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val medicine: Medicine? = null,
    val user : User? = null
    )


