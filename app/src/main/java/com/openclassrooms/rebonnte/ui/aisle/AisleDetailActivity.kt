package com.openclassrooms.rebonnte.ui.aisle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.rebonnte.ui.component.AisleDetailScreen
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel
import com.openclassrooms.rebonnte.ui.theme.RebonnteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AisleDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("nameAisle") ?: "Unknown"
        val viewModel = ViewModelProvider(this)[MedicineViewModel::class.java]
        setContent {
            RebonnteTheme {
                AisleDetailScreen(name, viewModel)
            }
        }
    }
}



