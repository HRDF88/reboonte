package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel
import com.openclassrooms.rebonnte.utils.accessibility.AccessibilityAnnouncer

@Composable
fun MedicineDetailScreen(name: String, viewModel: MedicineViewModel) {
    val state by viewModel.uiState.collectAsState()
    val medicines = state.medicine
    val medicine = medicines.find { it.name == name } ?: return
    var stock by remember { mutableIntStateOf(medicine.stock) }
    val userId = state.user?.email ?: "unknown"
    val context = LocalContext.current


    //Accessibility
    val medicineNameContentDescription =
        stringResource(R.string.medicine_name_content_description, medicine.name)
    val medicineStockContentDescription =
        stringResource(R.string.medicine_stock_content_description, medicine.name, medicine.stock)
    val medicineAisleContentDescription =
        stringResource(R.string.medicine_aisle_content_description, medicine.nameAisle)



    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = medicine.name,
                onValueChange = {},
                label = { Text("Name") },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = medicineNameContentDescription }

            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = medicine.nameAisle,
                onValueChange = {},
                label = { Text(stringResource(R.string.aisle)) },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = medicineAisleContentDescription }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    val detail = context.getString(
                        R.string.updated_medicine_details_with_stock,
                        stock,
                        stock - 1
                    )
                    viewModel.decrementStock(medicine, userId, detail)
                    stock--
                    AccessibilityAnnouncer.announce(
                        context,
                        context.getString(R.string.stock_action, medicine.name, stock)
                    )

                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.minus_One)
                    )
                }
                TextField(
                    value = medicine.stock.toString(),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.stock)) },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .semantics { contentDescription = medicineStockContentDescription }
                )
                IconButton(onClick = {
                    val detail = context.getString(
                        R.string.updated_medicine_details_with_stock,
                        stock,
                        stock + 1
                    )
                    viewModel.incrementStock(medicine, userId, detail)
                    stock++
                    AccessibilityAnnouncer.announce(
                        context,
                        context.getString(R.string.stock_action, medicine.name, stock)
                    )

                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(R.string.plus_one)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.history),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(medicine.histories) { history ->
                    HistoryItem(history = history)
                }
            }
        }
    }
}