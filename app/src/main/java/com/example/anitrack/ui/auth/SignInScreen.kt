package com.example.anitrack.ui.auth

import CustomTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anitrack.network.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isTimeout by remember { mutableStateOf(false) }

    val isUsernameValid = username.isNotEmpty()
    val isPasswordValid = password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign In to Anitrack",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))
        CustomTextField(label = "Username", onValueChange = { username = it }, isValid = isUsernameValid)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Password", isPassword = true, onValueChange = { password = it }, isValid = isPasswordValid)
        Spacer(modifier = Modifier.height(24.dp))



        Button(
            onClick = {
                if (isUsernameValid && isPasswordValid) {
                    isTimeout = false
                    authViewModel.signIn(username, password)
                    coroutineScope.launch {
                        delay(10000) // Espera de 10 segundos
                        if (authState is AuthState.Loading) {
                            isTimeout = true
                            authViewModel.setAuthError(AuthState.Error(Exception("Authentication timed out. Please try again.")))
                        }
                    }
                }
            },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            enabled = isUsernameValid && isPasswordValid
        ) {
            Text(
                text = "Sign In",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp) // Padding interno alrededor del texto
            )
        }

        when (authState) {
            is AuthState.Loading -> {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).exception.message ?: "Error",
                    color = Color.Red
                )
            }
            is AuthState.Success -> {
                LaunchedEffect(authState) { onSignInSuccess() }
            }
            else -> {}
        }

        if (isTimeout) {
            Text(
                text = "Authentication is taking longer than expected. Please check your connection and try again.",
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "New here? Sign Up",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onSignUpClick() }
        )
    }
}
