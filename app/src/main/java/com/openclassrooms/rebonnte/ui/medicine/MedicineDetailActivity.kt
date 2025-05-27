package com.openclassrooms.rebonnte.ui.medicine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.openclassrooms.rebonnte.ui.medecineDetail.MedicineDetailScreen
import com.openclassrooms.rebonnte.ui.theme.RebonnteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicineDetailActivity : ComponentActivity() {

    private val viewmodel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("nameMedicine") ?: "Unknown"


        setContent {
            RebonnteTheme {
                MedicineDetailScreen(name, viewmodel)
            }
        }
    }
}



