package com.openclassrooms.rebonnte.ui.addAisle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.domain.useCases.aisle.container.AisleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAisleViewModel @Inject constructor(private val aisleUseCase: AisleUseCases) :
    ViewModel() {

    private val _uiState = MutableStateFlow(AddAisleUiState())
    val uiState: StateFlow<AddAisleUiState> = _uiState


    fun addAisle(aisle: Aisle?) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                if (aisle != null) {
                    aisleUseCase.addAisleUseCase(aisle)
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

    /**
     * Resets the success or failure message in the UI state.
     *
     * This function is useful to clear any displayed message after it has been shown to the user.
     */
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}