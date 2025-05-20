package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Medicine

@Composable
fun MedicineItem(medicine: Medicine, onClick: (String) -> Unit,isTalkBackEnabled: Boolean,onDelete: (Medicine) -> Unit,) {

    //Accessibility
    val medicineNameContentDescription =
        stringResource(R.string.medicine_name_content_description, medicine.name)
    val medicineStockContentDescription =
        stringResource(R.string.medicine_stock_content_description, medicine.name, medicine.stock)
    val arrowDetailContentDescription = stringResource(R.string.click_for_details)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(medicine.name) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = medicine.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics {
                    contentDescription = medicineNameContentDescription
                })
            Text(
                text = "${stringResource(R.string.stock)} : ${medicine.stock}",
                color = Color.Gray,
                modifier = Modifier.semantics {
                    contentDescription = medicineStockContentDescription
                })
        }
        if (isTalkBackEnabled) {
            IconButton(
                onClick = { onDelete(medicine)},
                modifier = Modifier
                    .semantics { contentDescription = "Supprimer ${medicine.name}" }
                    .testTag("DeleteButton")
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = arrowDetailContentDescription
        )

    }
}