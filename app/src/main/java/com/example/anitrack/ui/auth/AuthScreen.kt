package com.example.anitrack.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    onSignInSuccess: () -> Unit
) {
    var showSignIn by remember { mutableStateOf(true) }

    if (showSignIn) {
        SignInScreen(
            modifier = modifier,
            authViewModel = authViewModel,
            onSignInSuccess = onSignInSuccess,   
            onSignUpClick = { showSignIn = false }
        )
    } else {
        SignUpScreen(
            modifier = modifier,
            authViewModel = authViewModel,
            onSignInSuccess = onSignInSuccess,
            onLoginClick = { showSignIn = true }
        )
    }
}
