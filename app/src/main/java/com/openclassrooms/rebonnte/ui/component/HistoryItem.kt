package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.History
import com.openclassrooms.rebonnte.utils.date.DateFormatter

@Composable
fun HistoryItem(history: History) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = history.medicineName, fontWeight = FontWeight.Bold)
            Text(text = "User: ${history.userId}")
            Text(text = "Date:  ${
                DateFormatter.parseToTimestamp(history.date)
                    ?.let { DateFormatter.formatToLocalizedDate(it) }
                    ?: stringResource(R.string.invalid_date)
            }")
            Text(text = "Details: ${history.details}")
        }
    }
}