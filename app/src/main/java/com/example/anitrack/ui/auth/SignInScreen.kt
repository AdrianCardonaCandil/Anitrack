package com.example.anitrack.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitrack.network.AuthState

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onSignInSuccess: () -> Unit,          // Callback for successful login
    onSignUpClick: () -> Unit             // Callback for navigating to sign-up screen
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5E5E5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In to Anitrack",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF222034))
                .padding(16.dp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomTextField(label = "Username", onValueChange = { username = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Password", isPassword = true, onValueChange = { password = it })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { authViewModel.signIn(username, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log In", color = Color.White)
        }

        // Display login result based on authState
        when (authState) {
            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).exception.message ?: "Error",
                    color = Color.Red
                )
            }
            is AuthState.Success -> {
                // Call onSignInSuccess to trigger navigation
                LaunchedEffect(Unit) {
                    onSignInSuccess()
                }
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "New here? Sign Up",
            color = Color(0xFF6886C5),
            modifier = Modifier.clickable { onSignUpClick() }
        )
    }
}

