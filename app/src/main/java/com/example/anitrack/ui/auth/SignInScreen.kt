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

@Composable
fun SignInScreen(onSignUpClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5E5E5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In",
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
            text = "Welcome back to Anitrack",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(label = "UserName")
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Password", isPassword = true)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "New here? Sign Up",
            color = Color(0xFF6886C5),
            modifier = Modifier.clickable { onSignUpClick() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Login action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log In", color = Color.White)
        }
    }
}
