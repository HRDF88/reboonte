package com.openclassrooms.rebonnte

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.rebonnte.domain.model.Aisle
import com.openclassrooms.rebonnte.ui.aisle.AisleItem
import com.openclassrooms.rebonnte.ui.aisle.AisleUiState
import com.openclassrooms.rebonnte.ui.component.ErrorComposable
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AisleScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testAisleScreenDisplaysItems() {
        val aisles = listOf(Aisle(name = "Aisle1"), Aisle(name = "Aisle2"))
        val fakeState = AisleUiState(
            aisle = aisles,
            isLoading = false,
            error = null
        )

        composeTestRule.setContent {
            AisleScreenSimple(uiState = fakeState)
        }

        composeTestRule.onNodeWithText("Aisle1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Aisle2")
            .assertIsDisplayed()
    }

    @Test
    fun testClickOnAisleItem() {
        val aisles = listOf(Aisle(name = "Aisle1"))
        val fakeState = AisleUiState(
            aisle = aisles,
            isLoading = false,
            error = null
        )

        var clickedAisleName = ""

        composeTestRule.setContent {
            AisleScreenSimple(
                uiState = fakeState,
                onAisleClick = { aisle -> clickedAisleName = aisle.name }
            )
        }

        composeTestRule.onNodeWithText("Aisle1")
            .performClick()

        assertEquals("Aisle1", clickedAisleName)
    }
}


@Composable
fun AisleScreenSimple(
    uiState: AisleUiState,
    onAisleClick: (Aisle) -> Unit = {}
) {
    val context = LocalContext.current

    val errorMessage = uiState.error?.let {
        stringResource(id = it)
    } ?: ""

    SideEffect {
        if (uiState.error != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

        }
    }

    if (uiState.isLoading) {
        LoadingComponent()
    }

    if (uiState.aisle != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.aisle) { aisle ->
                AisleItem(aisle = aisle, onClick = { onAisleClick(aisle) })
            }
        }
    } else {

        ErrorComposable {}
    }
}

