package com.example.anitrack.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
    var showSignIn by remember { mutableStateOf(true) }

    if (showSignIn) {
        SignInScreen(
            authViewModel = authViewModel,
            onSignUpClick = { showSignIn = false }
        )
    } else {
        SignUpScreen(
            authViewModel = authViewModel,
            onLoginClick = { showSignIn = true }
        )
    }
}
