package com.example.anitrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anitrack.ui.AnitrackApp
import com.example.anitrack.ui.global.BottomNavigationBar
import com.example.anitrack.ui.global.TopAppBar
import com.example.anitrack.ui.theme.AnitrackTheme
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            navController = rememberNavController()

            AnitrackTheme {
                Scaffold(
                    topBar = { TopAppBar(modifier = Modifier) },
                    bottomBar = {
                        BottomNavigationBar(navController = navController, modifier = Modifier)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AnitrackApp(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }

                // Manejar el intent inicial después de configurar NavController
                androidx.compose.runtime.LaunchedEffect(navController) {
                    handleDeepLink(intent)
                }
            }
        }

        observeDeepLink()
    }


    private fun observeDeepLink() {
        lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val currentIntent = intent
                if (currentIntent != null && currentIntent.data != null) {
                    handleDeepLink(currentIntent)
                }
            }
        })
    }


    private fun handleDeepLink(intent: Intent?) {
        val deepLinkUserId = intent?.data?.pathSegments?.lastOrNull()
        if (::navController.isInitialized && deepLinkUserId != null) {
            try {
                navController.navigate("profile/$deepLinkUserId")
                // Limpia el intent después de manejarlo
                this.intent = null
            } catch (e: IllegalArgumentException) {
                Log.e("DeepLink", "Navigation error: ${e.message}")
            }
        } else {
            Log.e("DeepLink", "Invalid or missing userId in deep link: ${intent?.data}")
        }
    }

}

