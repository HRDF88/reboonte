package com.openclassrooms.rebonnte.ui.medicine

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.ui.component.ErrorComposable
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import com.openclassrooms.rebonnte.ui.component.MedicineItem

@Composable
fun MedicineScreen(viewModel: MedicineViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()
    val medicines = state.medicine
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

    when {
        state.isLoading -> {
            LoadingComponent()
        }

        medicines == null -> {
            ErrorComposable { viewModel.loadAllMedicine() }
        }

        medicines.isEmpty() -> {
            Text(
                text = stringResource(R.string.no_results_found),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(medicines) { medicine ->
                    MedicineItem(medicine = medicine, onClick = {
                        startDetailActivity(context, medicine.name)
                    })
                }
            }
        }
    }
}

private fun startDetailActivity(context: Context, name: String) {
    val intent = Intent(context, MedicineDetailActivity::class.java).apply {
        putExtra("nameMedicine", name)
    }
    context.startActivity(intent)
}
