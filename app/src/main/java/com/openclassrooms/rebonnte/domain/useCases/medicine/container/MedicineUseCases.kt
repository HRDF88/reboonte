package com.openclassrooms.rebonnte.domain.useCases.medicine.container

import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.AddMedicineUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.DeleteMedicineByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetAllMedicinesUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.GetMedicinesSortedByStockUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.SearchMedicinesByNameUseCase
import com.openclassrooms.rebonnte.domain.useCases.medicine.useCase.UpdateMedicineUseCase
import javax.inject.Inject

data class MedicineUseCases @Inject constructor(
    val addMedicine: AddMedicineUseCase,
    val getAllMedicines: GetAllMedicinesUseCase,
    val getMedicinesSortedByName: GetMedicinesSortedByNameUseCase,
    val getMedicinesSortedByStock: GetMedicinesSortedByStockUseCase,
    val searchMedicinesByName: SearchMedicinesByNameUseCase,
    val updateMedicine: UpdateMedicineUseCase,
    val deleteMedicineByName: DeleteMedicineByNameUseCase
)