package com.example.anitrack.ui.auth

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anitrack.R
import com.example.anitrack.network.AuthState
import com.example.anitrack.ui.profile.CustomTextField


@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onSignUpClick: () -> Unit
) {


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()
    val isUsernameValid = username.isNotEmpty()
    val isPasswordValid = password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.sign_in_title),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))
        CustomTextField(
            label = stringResource(R.string.username),
            onValueChange = { username = it },
            isValid = isUsernameValid
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            label = stringResource(R.string.password),
            isPassword = true,
            onValueChange = { password = it },
            isValid = isPasswordValid
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.new_here_sign_up),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onSignUpClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isUsernameValid && isPasswordValid) {
                    authViewModel.signIn(username, password)
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
                text = stringResource(R.string.sign_in),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        when (authState) {
            is AuthState.Loading -> {
                Text(text = stringResource(R.string.loading), color = Color.Gray)
            }

            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).exception.message
                        ?: stringResource(R.string.unknown_error),
                    color = Color.Red
                )
            }

            is AuthState.ValidationError -> {
                Text(text = (authState as AuthState.ValidationError).message, color = Color.Red)
            }

            is AuthState.Success -> {
                Text(text = stringResource(R.string.sign_up_successful), color = Color.Green)
            }

            else -> {}
        }
    }
}
