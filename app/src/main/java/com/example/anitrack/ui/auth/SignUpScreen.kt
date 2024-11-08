package com.example.anitrack.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitrack.network.AuthState

@Composable
fun SignUpScreen(authViewModel: AuthViewModel, onLoginClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5E5E5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF222034))
                .padding(16.dp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Welcome to Anitrack",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(label = "Username", onValueChange = { username = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Email", onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Password", isPassword = true, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Repeat Password", isPassword = true, onValueChange = { confirmPassword = it })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Not new here? Log In",
            color = Color(0xFF6886C5),
            modifier = Modifier.clickable { onLoginClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.signUp(username, email, password, confirmPassword) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign Up", color = Color.White)
        }

        // Handle auth state
        when (authState) {
            is AuthState.Loading -> {
                Text(text = "Loading...", color = Color.Gray)
            }
            is AuthState.Error -> {
                Text(text = (authState as AuthState.Error).exception.message ?: "Unknown error", color = Color.Red)
            }
            is AuthState.ValidationError -> {
                Text(text = (authState as AuthState.ValidationError).message, color = Color.Red)
            }
            is AuthState.Success -> {
                Text(text = "Sign-up successful!", color = Color.Green)
            }
            else -> {}
        }
    }
}
