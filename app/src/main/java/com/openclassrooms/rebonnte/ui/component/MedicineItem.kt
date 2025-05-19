package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.domain.model.Medicine

@Composable
fun MedicineItem(medicine: Medicine, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(medicine.name) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = medicine.name, fontWeight = FontWeight.Bold)
            Text(text = "Stock: ${medicine.stock}", color = Color.Gray)
        }
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow")
    }
}