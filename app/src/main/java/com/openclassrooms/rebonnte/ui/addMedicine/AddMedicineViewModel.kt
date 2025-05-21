package com.openclassrooms.rebonnte.ui.addMedicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.aisle.container.AisleUseCases
import com.openclassrooms.rebonnte.domain.useCases.medicine.container.MedicineUseCases
import com.openclassrooms.rebonnte.domain.useCases.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and logic related to adding medicines.
 *
 * It handles loading the current user, fetching all aisles,
 * and adding a new medicine to the repository.
 *
 * @property medicineUseCase Use cases related to medicine operations.
 * @property userUseCases Use cases related to user operations.
 * @property aisleUseCases Use cases related to aisle operations.
 */
@HiltViewModel
open class AddMedicineViewModel @Inject constructor(
    private val medicineUseCase: MedicineUseCases,
    private val userUseCases: UserUseCases,
    private val aisleUseCases: AisleUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddMedicineUiState())
    open val uiState: StateFlow<AddMedicineUiState> = _uiState

    init {
        loadUser()
        loadAllAisle()
    }

    /**
     * Adds a new medicine using the provided medicine data.
     *
     * Updates the UI state to reflect loading status,
     * success or failure messages.
     *
     * @param medicine The medicine to add. If null, no operation is performed.
     */
    fun addMedicine(medicine: Medicine?) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                if (medicine != null) {
                    medicineUseCase.addMedicine(medicine)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = R.string.add_medicine_success
                    )

                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_add_medicine
                )
            }

        }
    }

    /**
     * Loads the currently authenticated user.
     *
     * Updates the UI state with user data or error status.
     */
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
                    error = R.string.error_load_user
                )
            }
        }
    }

    /**
     * Loads the list of all aisles.
     *
     * Collects aisles from the use case flow and updates the UI state accordingly.
     */
    private fun loadAllAisle() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                aisleUseCases.getAllAislesUseCase().collect { aisles ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        aisle = aisles
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_load_aisle
                )
            }
        }
    }


    /**
     * Resets the success or failure message in the UI state.
     *
     * This function is useful to clear any displayed message after it has been shown to the user.
     */
    open fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null, successMessage = null)
    }

}

