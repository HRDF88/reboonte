package com.openclassrooms.rebonnte.ui.medicine

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.ui.component.ErrorComposable
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import com.openclassrooms.rebonnte.ui.component.MedicineItem
import com.openclassrooms.rebonnte.utils.accessibility.AccessibilityAnnouncer
import com.openclassrooms.rebonnte.utils.accessibility.AccessibilityTalkBackEnabled
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MedicineScreen(viewModel: MedicineViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()
    val medicines = state.medicine
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isTalkBack = AccessibilityTalkBackEnabled.isTalkBackEnabled(context)

    var medicineToDelete by remember { mutableStateOf<Medicine?>(null) }

    LaunchedEffect(state.successMessage) {
        state.successMessage?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_SHORT).show()
            viewModel.resetMessage()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, context.getString(it), Toast.LENGTH_SHORT).show()
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
            medicineToDelete?.let { medicine ->
                AlertDialog(
                    onDismissRequest = { medicineToDelete = null },
                    title = { Text(stringResource(R.string.confirm_delete_title)) },
                    text = { Text(stringResource(R.string.confirm_delete_message)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteMedicine(medicine.name)
                                    AccessibilityAnnouncer.announce(
                                        context,
                                        context.getString(
                                            R.string.medicine_deleted_announcement,
                                            medicine.name
                                        )
                                    )
                                    medicineToDelete = null
                                }
                            }
                        ) {
                            Text(stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { medicineToDelete = null }) {
                            Text(stringResource(R.string.no))
                        }
                    }
                )
            }

            val handleDelete: (Medicine) -> Unit = { selectedMedicine ->
                medicineToDelete = selectedMedicine
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = medicines,
                    key = { it.name }
                ) { medicine ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                medicineToDelete = medicine
                                false
                            } else false
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        background = {
                            val color by animateColorAsState(
                                targetValue = if (dismissState.targetValue == DismissValue.Default) Color.Transparent else Color.Red,
                                label = "swipeColor"
                            )

                            val alignment = Alignment.CenterStart
                            val icon = Icons.Default.Delete

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(start = 20.dp),
                                contentAlignment = alignment
                            ) {
                                if (dismissState.targetValue != DismissValue.Default) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = stringResource(
                                            R.string.delete_medicine_description,
                                            medicine.name
                                        ),
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        dismissContent = {
                            MedicineItem(medicine = medicine, onClick = {
                                startDetailActivity(context, medicine.name)
                            }, isTalkBackEnabled = isTalkBack, onDelete = handleDelete)
                        }
                    )
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
