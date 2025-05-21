package com.openclassrooms.rebonnte

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.openclassrooms.rebonnte.ui.addAisle.AddAisleScreen
import com.openclassrooms.rebonnte.ui.addMedicine.AddMedicineScreen
import com.openclassrooms.rebonnte.ui.aisle.AisleScreen
import com.openclassrooms.rebonnte.ui.aisle.AisleViewModel
import com.openclassrooms.rebonnte.ui.auth.AuthViewModel
import com.openclassrooms.rebonnte.ui.component.AisleDetailScreen
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import com.openclassrooms.rebonnte.ui.component.MedicineDetailScreen
import com.openclassrooms.rebonnte.ui.component.MyApp
import com.openclassrooms.rebonnte.ui.medicine.MedicineScreen
import com.openclassrooms.rebonnte.ui.medicine.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var myBroadcastReceiver: MyBroadcastReceiver
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var signInLauncher:
            ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result ->
            authViewModel.handleSignInResult(result)
        }
        setContent {
            val user by authViewModel.user.collectAsState()
            val navController = rememberNavController()
            val medicineViewModel: MedicineViewModel = hiltViewModel()
            val aisleViewModel: AisleViewModel = hiltViewModel()

            LaunchedEffect(user) {
                if (user == null) {
                    authViewModel.startSignIn(signInLauncher)
                }
            }

            if (user != null) {
                MyApp(
                    navController = navController,
                    medicineViewModel = medicineViewModel,
                    aisleViewModel = aisleViewModel)
            } else {

                LoadingComponent()
            }
        }

        startBroadcastReceiver()
    }


    private fun startMyBroadcast() {
        val intent = Intent("com.rebonnte.ACTION_UPDATE")
        sendBroadcast(intent)
        //startBroadcastReceiver() infinite loop
    }

    private fun startBroadcastReceiver() {
        myBroadcastReceiver = MyBroadcastReceiver()
        val filter = IntentFilter().apply {
            addAction("com.rebonnte.ACTION_UPDATE")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(myBroadcastReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(myBroadcastReceiver, filter)
        }

        Handler().postDelayed({
            startMyBroadcast()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
    }


    class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Update reçu", Toast.LENGTH_SHORT).show()
        }
    }
}



@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    medicineViewModel: MedicineViewModel,
    aisleViewModel: AisleViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "aisle",
        modifier = modifier
    ) {
        composable("aisle") { AisleScreen(aisleViewModel) }
        composable("medicine") { MedicineScreen(medicineViewModel) }
        composable("addAisle") {
            AddAisleScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable("addMedicine") {
            AddMedicineScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        composable(
            route = "aisleDetail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
            val name = it.arguments?.getString("name") ?: "Unknown"
            AisleDetailScreen(name = name, viewModel = hiltViewModel())
        }
        composable(
            route = "medicineDetail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) {
            val name = it.arguments?.getString("name") ?: "Unknown"
            MedicineDetailScreen(name = name, viewModel = hiltViewModel())
        }
    }
}



