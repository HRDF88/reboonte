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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.openclassrooms.rebonnte.ui.auth.AuthViewModel
import com.openclassrooms.rebonnte.ui.component.LoadingComponent
import com.openclassrooms.rebonnte.ui.component.MyApp
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

            LaunchedEffect(user) {
                if (user == null) {
                    authViewModel.startSignIn(signInLauncher)
                }
            }

            if (user != null) {
                MyApp()
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

    companion object {
        lateinit var mainActivity: MainActivity
    }
}



@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


