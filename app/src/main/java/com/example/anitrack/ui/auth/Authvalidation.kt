package com.example.anitrack.ui.auth

import android.util.Patterns

fun validateSignUp(username: String, email: String, password: String, confirmPassword: String): Boolean {
    // Check if any fields are empty
    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        return false
    }

    // Check if email is valid
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return false
    }

    // Check if password meets criteria: at least 8 characters, contains uppercase, and a digit
    if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
        return false
    }

    // Check if password and confirmPassword match
    if (password != confirmPassword) {
        return false
    }

    return true
}
