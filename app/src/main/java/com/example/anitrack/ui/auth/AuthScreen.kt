package com.example.anitrack.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.example.anitrack.network.AuthState

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignSuccess: () -> Unit
) {
    var showSignIn by remember { mutableStateOf(true) }
    val authState by authViewModel.authState.collectAsState()
    val isLoading = authState is AuthState.Loading

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onSignSuccess()
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (showSignIn) {
            SignInScreen(
                modifier = modifier,
                authViewModel = authViewModel,
                onSignInSuccess = onSignSuccess,
                onSignUpClick = { showSignIn = false }
            )
        } else {
            SignUpScreen(
                modifier = modifier,
                authViewModel = authViewModel,
                onSignUpSuccess = onSignSuccess,
                onLoginClick = { showSignIn = true }
            )
        }
    }
}
