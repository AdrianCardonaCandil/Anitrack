package com.example.anitrack.ui.auth

import CustomTextField
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anitrack.network.AuthState

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    onSignInSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    val isUsernameValid = username.length >= 2
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() }
    val isPasswordMatch = password == confirmPassword

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up to Anitrack",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomTextField(label = "Username", onValueChange = { username = it }, isValid = isUsernameValid)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Email", onValueChange = { email = it }, isValid = isEmailValid)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Password", isPassword = true, onValueChange = { password = it }, isValid = isPasswordValid)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Repeat Password", isPassword = true, onValueChange = { confirmPassword = it }, isValid = isPasswordMatch)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Not new here? Sign In",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onLoginClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.signUp(username, email, password, confirmPassword) },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            enabled = isUsernameValid && isEmailValid && isPasswordValid && isPasswordMatch
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
