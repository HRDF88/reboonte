package com.openclassrooms.rebonnte

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.rebonnte.ui.component.AddAisleForm
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class FakeAddAisleViewModel {
    var name by mutableStateOf("")
    fun onNameChange(newName: String) {
        name = newName
    }
    fun onValidateClick() {

    }
}

@RunWith(AndroidJUnit4::class)
class AddAisleFormTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testTextInputAndButtonEnabledState() {
        val viewModel = FakeAddAisleViewModel()

        composeTestRule.setContent {
            AddAisleForm(
                name = viewModel.name,
                onNameChange = { viewModel.onNameChange(it) },
                onClick = { viewModel.onValidateClick() }
            )
        }

        composeTestRule.onNodeWithTag("AisleNameInput")
            .assertIsDisplayed()
            .performTextInput("atari")

        composeTestRule.onNodeWithTag("AisleNameInput")
            .assertTextContains("atari")

        composeTestRule.onNodeWithText("Validate")
            .assertIsEnabled()

    }
}
