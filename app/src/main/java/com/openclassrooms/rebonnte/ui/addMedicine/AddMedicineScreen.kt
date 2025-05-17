package com.openclassrooms.rebonnte.ui.addMedicine

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.openclassrooms.rebonnte.R
import com.openclassrooms.rebonnte.domain.model.History
import com.openclassrooms.rebonnte.domain.model.Medicine
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import com.openclassrooms.rebonnte.ui.theme.vertRebonnte

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAisleScreen(
    navController: NavController,
    viewModel: AddMedicineViewModel
) {

    val medicineState by viewModel.uiState.collectAsState()

    val errorMessage = medicineState.error?.let {
        stringResource(id = it)
    } ?: ""

    val context = LocalContext.current
    val backButton = stringResource(R.string.back_button)
    val createdMedicineMessage = stringResource(id = R.string.created_medicine)


    var name by remember { mutableStateOf("") }
    var aisle by remember { mutableStateOf("") }
    val user = medicineState.user?.email.toString()
    var addTrigger by remember { mutableStateOf(false) }

    if (addTrigger) {
        LaunchedEffect(addTrigger) {
            val medicineToAdd = Medicine(
                name = name,
                stock = 1,
                nameAisle = aisle,
                histories = listOf(
                    History(
                        date = Timestamp.now().toString(),
                        medicineName = name,
                        details = createdMedicineMessage,
                        userId = user
                    )
                )
            )
            viewModel.addAisle(medicineToAdd)
            addTrigger = false // reset
            navController.popBackStack()
        }
    }


    SideEffect {
        if (medicineState.error != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetMessage()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = backButton,
                            tint = Color.White,
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.add_tittle_medicine),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = vertRebonnte
                ),

                )
        }
    ) { innerPadding ->
        if (medicineState.isLoading) {

            LoadingComponent()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

        }
    }
}
