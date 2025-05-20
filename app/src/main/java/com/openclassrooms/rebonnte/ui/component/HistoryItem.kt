package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.History
import com.openclassrooms.rebonnte.utils.date.DateFormatter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HistoryItem(history: History) {
    val context = LocalContext.current

    // Accessibility
    val announcementText = context.getString(
        R.string.history_announcement,
        history.medicineName,
        history.userId,
        DateFormatter.parseToTimestamp(history.date)
            ?.let { DateFormatter.formatToLocalizedDate(it) }
            ?: context.getString(R.string.invalid_date),
        history.details
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = history.medicineName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.semantics { contentDescription = announcementText })
            Text(
                text = "${stringResource(R.string.user)} ${history.userId}",
                modifier = Modifier.semantics { invisibleToUser() })
            Text(text = "${stringResource(R.string.date)}  ${
                DateFormatter.parseToTimestamp(history.date)
                    ?.let { DateFormatter.formatToLocalizedDate(it) }
                    ?: stringResource(R.string.invalid_date)
            }", modifier = Modifier.semantics { invisibleToUser() })
            Text(
                text = "${stringResource(R.string.history)} ${history.details}",
                modifier = Modifier.semantics { invisibleToUser() })
        }
    }
}