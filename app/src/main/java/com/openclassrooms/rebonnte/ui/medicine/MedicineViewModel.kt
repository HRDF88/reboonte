package com.openclassrooms.rebonnte.ui.medicine

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.rebonnte.MainActivity
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.History
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.medicine.container.MedicineUseCases
import com.openclassrooms.rebonnte.domain.useCases.user.container.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel responsible for managing UI state related to medicines.
 *
 * Handles loading, filtering, sorting, updating stock, and deleting medicines,
 * as well as managing the current logged-in user state.
 *
 * @property medicineUseCase Use cases related to medicine operations.
 * @property userUseCases Use cases related to user operations.
 */
@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineUseCase: MedicineUseCases,
    private val userUseCases: UserUseCases,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MedicineUiState())
    val uiState: StateFlow<MedicineUiState> = _uiState


    init {
        loadAllMedicine()
        loadUser()
    }

    /**
     * Loads all medicines and updates the UI state accordingly.
     */
    fun loadAllMedicine() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                medicineUseCase.getAllMedicines().collect { medicines ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        medicine = medicines
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_load_all_medicine
                )
            }
        }
    }

    /**
     * Filters medicines by their name matching the given search string.
     *
     * @param search The search query string.
     */
    fun filterByName(search: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                medicineUseCase.searchMedicinesByName(search).collect { medicines ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        medicine = medicines
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_load_medicine_by_search
                )
            }
        }
    }

    /**
     * Sorts the list of medicines by their name.
     */
    fun sortByName() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                medicineUseCase.getMedicinesSortedByName().collect { medicines ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        medicine = medicines
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_load_medicine_by_name
                )
            }
        }
    }

    /**
     * Sorts the list of medicines by their stock quantity.
     */
    fun sortByStock() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                medicineUseCase.getMedicinesSortedByStock().collect { medicines ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        medicine = medicines
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.error_load_medicine_by_stock
                )
            }
        }
    }

    /**
     * Loads the current logged-in user and updates the UI state.
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
     * Updates the stock of a given medicine and logs the change in its history.
     *
     * @param medicine The medicine to update.
     * @param newStock The new stock value.
     * @param userId The ID of the user who made the change.
     * @param details Details describing the stock update.
     */
    private fun updateMedicineStock(
        medicine: Medicine,
        newStock: Int,
        userId: String,
        details: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val updatedHistories = medicine.histories.toMutableList().apply {
                add(
                    History(
                        medicineName = medicine.name,
                        userId = userId,
                        date = Date().toString(),
                        details = details
                    )
                )
            }
            val updatedMedicine = medicine.copy(
                stock = newStock,
                histories = updatedHistories
            )

            try {
                medicineUseCase.updateMedicine(updatedMedicine)

                val updatedList = _uiState.value.medicine


                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    medicine = updatedList
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = R.string.update_failed
                )

            }

        }
    }

    /**
     * Increments the stock of a medicine by one.
     *
     * @param medicine The medicine to update.
     * @param userId The ID of the user performing the update.
     * @param detail Details describing the stock increment.
     */
    fun incrementStock(medicine: Medicine, userId: String?, detail: String) {

        val newStock = medicine.stock + 1
        if (userId != null) {
            updateMedicineStock(medicine, newStock, userId, detail)
        }
    }

    /**
     * Decrements the stock of a medicine by one, preventing negative stock.
     *
     * @param medicine The medicine to update.
     * @param userId The ID of the user performing the update.
     * @param detail Details describing the stock decrement.
     */
    fun decrementStock(
        medicine: Medicine,
        userId: String?,
        detail: String
    ) {
        val oldStock = medicine.stock
        if (oldStock <= 0) return
        val newStock = oldStock - 1
        if (userId != null) {
            updateMedicineStock(medicine, newStock, userId, detail)
        }
    }

    /**
     * Logs out the current user, clears the user state,
     * and restarts the app's main activity.
     *
     * @param context The context used to start the main activity.
     */
    fun logOut(context: Context) {
        viewModelScope.launch {
            userUseCases.signOut()
            _user.value = null
            _uiState.value = _uiState.value.copy(user = null)
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    /**
     * Deletes a medicine by its name and updates the UI state accordingly.
     *
     * @param nameMedicine The name of the medicine to delete.
     */
    suspend fun deleteMedicine(nameMedicine: String) {
        try {
            viewModelScope.launch {
                medicineUseCase.deleteMedicineByName(nameMedicine)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = R.string.delete_medicine_success
                )
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = R.string.error_delete_medicine
            )
        }
    }

    private val _user = MutableStateFlow<User?>(userUseCases.getCurrentUser())
    val user: StateFlow<User?> = _user.asStateFlow()

    /**
     * Resets error and success messages in the UI state.
     */
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

