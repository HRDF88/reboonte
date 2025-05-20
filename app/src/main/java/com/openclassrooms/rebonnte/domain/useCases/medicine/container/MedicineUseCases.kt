package com.openclassrooms.rebonnte.domain.useCases.medicine.container

import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.AddMedicineUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.DeleteMedicineByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetAllMedicinesUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByStockUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.SearchMedicinesByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.UpdateMedicineUseCase
import javax.inject.Inject

/**
 * Aggregates all use cases related to medicine management.
 *
 * @property addMedicine Use case for adding a new medicine.
 * @property getAllMedicines Use case for retrieving all medicines.
 * @property getMedicinesSortedByName Use case for retrieving medicines sorted by name.
 * @property getMedicinesSortedByStock Use case for retrieving medicines sorted by stock quantity.
 * @property searchMedicinesByName Use case for searching medicines by their name.
 * @property updateMedicine Use case for updating an existing medicine.
 * @property deleteMedicineByName Use case for deleting a medicine by its name.
 */
data class MedicineUseCases @Inject constructor(
    val addMedicine: AddMedicineUseCase,
    val getAllMedicines: GetAllMedicinesUseCase,
    val getMedicinesSortedByName: GetMedicinesSortedByNameUseCase,
    val getMedicinesSortedByStock: GetMedicinesSortedByStockUseCase,
    val searchMedicinesByName: SearchMedicinesByNameUseCase,
    val updateMedicine: UpdateMedicineUseCase,
    val deleteMedicineByName: DeleteMedicineByNameUseCase
)