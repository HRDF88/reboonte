package com.openclassrooms.rebonnte.ui.aisle

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.ui.component.ErrorComposable
import com.openclassrooms.rebonnte.ui.component.LoadingComponent

@Composable
fun AisleScreen(viewModel: AisleViewModel) {
    val state by viewModel.uiState.collectAsState()
    val errorMessage = state.error?.let {
        stringResource(id = it)
    } ?: ""
    val context = LocalContext.current


    SideEffect {
        if (state.error != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetMessage()
        }
    }
    if (state.isLoading) {

        LoadingComponent()
    }

    if (state.aisle != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(state.aisle) { aisle ->
                AisleItem(aisle = aisle, onClick = {
                    startDetailActivity(context, aisle.name)
                })
            }
        }
    } else {
        ErrorComposable { viewModel.loadAllAisle() }
    }
}



@Composable
fun AisleItem(aisle: Aisle, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = aisle.name, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow")
    }
}

private fun startDetailActivity(context: Context, name: String) {
    val intent = Intent(context, AisleDetailActivity::class.java).apply {
        putExtra("nameAisle", name)
    }
    context.startActivity(intent)
}