package com.example.anitrack.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AuthScreen() {
    var showSignIn by remember { mutableStateOf(true) }

    if (showSignIn) {
        SignInScreen { showSignIn = false }
    } else {
        SignUpScreen { showSignIn = true }
    }
}
