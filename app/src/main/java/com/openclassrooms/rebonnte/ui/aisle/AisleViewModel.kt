package com.openclassrooms.rebonnte.ui.aisle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.useCases.aisle.container.AisleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of aisles.
 *
 * It handles loading all aisles and exposing the current UI state via a [StateFlow].
 *
 * @property aisleUseCases Use cases for aisle-related operations.
 */
@HiltViewModel
class AisleViewModel @Inject constructor(private val aisleUseCases: AisleUseCases) : ViewModel() {
    private val _uiState = MutableStateFlow(AisleUiState())
    val uiState: StateFlow<AisleUiState> = _uiState

    init {
        loadAllAisle()
    }

    /**
     * Loads all aisles and updates the UI state accordingly.
     *
     * Sets [AisleUiState.isLoading] to true at the start, then collects the aisles list.
     * On success, updates the aisles list and clears loading and error states.
     * On failure, sets an error message resource ID.
     */
    fun loadAllAisle() {
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
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

