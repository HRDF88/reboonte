package com.openclassrooms.rebonnte.ui.addMedicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.medicine.container.MedicineUseCases
import com.openclassrooms.rebonnte.domain.useCases.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
    private val medicineUseCase: MedicineUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddMedicineUiState())
    val uiState: StateFlow<AddMedicineUiState> = _uiState

    init {
        loadUser()
    }

    fun addAisle(medicine: Medicine?) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                if (medicine != null) {
                    medicineUseCase.addMedicine(medicine)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                    )

                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.success_false_message
                )
            }

        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val domainUser: User? = userUseCases.getCurrentUser()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = domainUser
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.success_false_message
                )
            }
        }
    }



    /**
     * Resets the success or failure message in the UI state.
     *
     * This function is useful to clear any displayed message after it has been shown to the user.
     */
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}

