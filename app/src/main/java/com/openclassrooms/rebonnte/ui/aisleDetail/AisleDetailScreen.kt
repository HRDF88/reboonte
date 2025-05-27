package com.openclassrooms.rebonnte.ui.aisleDetail

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.ui.component.MedicineItem
import com.openclassrooms.rebonnte.ui.medicine.MedicineDetailActivity
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AisleDetailScreen(name: String, viewModel: MedicineViewModel) {
    val state by viewModel.uiState.collectAsState()
    val medicines = state.medicine
    val filteredMedicines = medicines.filter { it.nameAisle == name }
    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back_button
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (filteredMedicines.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_medicine_in_aisle))
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredMedicines) { medicine ->
                    MedicineItem(medicine = medicine, onClick = { name ->
                        val intent = Intent(context, MedicineDetailActivity::class.java).apply {
                            putExtra("nameMedicine", name)
                        }
                        context.startActivity(intent)
                    }, isTalkBackEnabled = false, onDelete = {})
                }
            }
        }
    }
}