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
                    error = R.string.error_load_aisle
                )
            }
        }
    }

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
                    error = R.string.error_load_aisle
                )
            }
        }
    }

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
                    error = R.string.error_load_aisle
                )
            }
        }
    }

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
                    error = R.string.error_load_aisle
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
    fun incrementStock(medicine: Medicine, userId: String?, detail: String) {

        val newStock = medicine.stock + 1
        if (userId != null) {
            updateMedicineStock(medicine, newStock, userId, detail)
        }
    }

    fun decrementStock(
        medicine: Medicine,
        userId: String?,
        detail : String
    ) {
        val oldStock = medicine.stock
        if (oldStock <= 0) return
        val newStock = oldStock - 1
        if (userId != null) {
            updateMedicineStock(medicine, newStock, userId, detail)
        }
    }

    fun logOut(context : Context) {
        viewModelScope.launch {
            userUseCases.signOut()
            _user.value = null
            _uiState.value = _uiState.value.copy(user = null)
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private val _user = MutableStateFlow<User?>(userUseCases.getCurrentUser())
    val user: StateFlow<User?> = _user.asStateFlow()


    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

