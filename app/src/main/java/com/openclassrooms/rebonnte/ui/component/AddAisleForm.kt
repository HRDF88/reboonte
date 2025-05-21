package com.openclassrooms.rebonnte.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.utils.text.TextUtils

@Composable
fun AddAisleForm(
    name: String,
    onNameChange: (String) -> Unit,
    onClick: () -> Unit
) {
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
                onNameChange(TextUtils.formatAisleName(newText))
            },
            label = { Text(stringResource(R.string.add_aisle)) },
            modifier = Modifier.testTag("AisleNameInput"),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClick,
            enabled = name.isNotBlank()


        ) { Text(text = stringResource(R.string.validate)) }
    }
}