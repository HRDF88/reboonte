package com.openclassrooms.rebonnte

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.ui.addMedicine.AddMedicineUiState
import com.openclassrooms.rebonnte.ui.component.AddMedicineForm
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddMedicineScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testAddMedicineScreenUI() {
        val aisles = listOf(Aisle(name = "Aisle1"), Aisle(name = "Aisle2"))

        val fakeUiState = AddMedicineUiState(
            aisle = aisles,
            user = User(id = "1", name = "Jocelyn Testing", email = "jocelyn.testing@gmail.com"),
            isLoading = false,
            successMessage = null,
            error = null
        )

        composeTestRule.setContent {
            AddMedicineScreenSimple(
                navController = rememberNavController(),
                uiState = fakeUiState
            )
        }


        composeTestRule.onNodeWithTag("MedicineNameInput")
            .assertIsDisplayed()
            .performTextInput("Paracetamol")

        composeTestRule.onNodeWithTag("AisleDropdownTrigger")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("AisleItem_Aisle1")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("ValidateButton")
            .assertIsEnabled()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddMedicineScreenSimple(
        navController: NavController,
        uiState: AddMedicineUiState
    ) {
        var name by remember { mutableStateOf("") }
        val listAisle = uiState.aisle
        var selectedAisle by remember { mutableStateOf<Aisle?>(null) }
        var addTrigger by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Add Medicine") })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                AddMedicineForm(
                    name = name,
                    onNameChange = { name = it },
                    onClick = { addTrigger = true },
                    aisleList = listAisle,
                    selectedAisles = listOfNotNull(selectedAisle),
                    onAisleSelected = { selectedAisle = it },
                    onAisleUnselected = { selectedAisle = null }
                )
            }
        }
    }
}