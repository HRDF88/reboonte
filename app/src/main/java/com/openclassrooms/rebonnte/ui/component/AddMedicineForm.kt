package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.utils.text.TextUtils

@Composable
fun AddMedicineForm(
    name: String,
    onNameChange: (String) -> Unit,
    onClick: () -> Unit,
    aisleList: List<Aisle>,
    selectedAisles: List<Aisle>,
    onAisleSelected: (Aisle) -> Unit,
    onAisleUnselected: (Aisle) -> Unit,
    enabled: Boolean = true
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        TextField(
            value = name,
            onValueChange = { newText ->
                onNameChange(TextUtils.formatMedicineName(newText))
            },
            label = { Text(stringResource(R.string.name_medicine)) },
            enabled = enabled,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clickable { isDropdownExpanded = !isDropdownExpanded }
                .padding(16.dp)
        ) {
            Text(
                text = if (selectedAisles.isEmpty()) stringResource(R.string.aisle_medicine)
                else selectedAisles.joinToString(", ") { it.name }
            )
        }

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            aisleList.forEach { aisle ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedAisles.contains(aisle),
                                onCheckedChange = {
                                    if (enabled) {
                                        if (it) onAisleSelected(aisle)
                                        else onAisleUnselected(aisle)
                                    }
                                },
                                enabled = enabled
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(aisle.name)
                        }
                    },
                    onClick = {
                        val alreadySelected = selectedAisles.contains(aisle)
                        if (alreadySelected) onAisleUnselected(aisle)
                        else onAisleSelected(aisle)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClick,
            enabled = enabled && name.isNotBlank() && selectedAisles.isNotEmpty()


        ) { Text(text = stringResource(R.string.validate)) }
    }
}