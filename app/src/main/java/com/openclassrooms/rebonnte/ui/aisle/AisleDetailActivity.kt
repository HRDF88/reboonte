package com.openclassrooms.rebonnte.ui.aisle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.rebonnte.ui.aisleDetail.AisleDetailScreen
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel
import com.openclassrooms.rebonnte.ui.theme.RebonnteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AisleDetailActivity : ComponentActivity() {

    private val viewmodel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("nameAisle") ?: "Unknown"

        setContent {
            RebonnteTheme {
                AisleDetailScreen(name, viewmodel)
            }
        }
    }
}



