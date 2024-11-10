package com.example.anitrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.AuthRepository
import com.example.anitrack.network.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow<Boolean>(firebaseAuth.currentUser != null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        // Monitor Firebase Auth state
        firebaseAuth.addAuthStateListener { auth ->
            _isLoggedIn.value = auth.currentUser != null
        }
    }

    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (validateSignUp(username, email, password, confirmPassword)) {
            _authState.update { AuthState.Loading() }
            viewModelScope.launch {
                val userExists = authRepository.isUsernameTaken(username)
                val emailExists = authRepository.isEmailTaken(email)
                if (userExists) {
                    _authState.update { AuthState.ValidationError("Username already taken") }
                } else if (emailExists) {
                    _authState.update { AuthState.ValidationError("Email already registered") }
                } else {
                    val result = authRepository.signUp(email, password)
                    _authState.update { result }
                    if (result is AuthState.Success) {
                        _isLoggedIn.update { true }
                    }
                }
            }
        } else {
            _authState.update { AuthState.ValidationError("Invalid input data") }
        }
    }

    fun signIn(username: String, password: String) {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val userExists = authRepository.validateSignIn(username, password)
            if (userExists is AuthState.Error) {
                _authState.update { AuthState.ValidationError("User not found or incorrect credentials") }
            } else {
                val result = authRepository.validateSignIn(username, password)
                _authState.update { result }

                if (result is AuthState.Success) {
                    _isLoggedIn.update { true }
                }
            }
        }
    }

    fun setAuthError(authState: AuthState.Error) {
        _authState.update { authState }
    }

    private fun validateSignUp(username: String, email: String, password: String, confirmPassword: String): Boolean {
        // Username should be at least 2 characters
        if (username.length < 2) {
            _authState.update { AuthState.ValidationError("Username must be at least 2 characters long") }
            return false
        }

        // Check if any fields are empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _authState.update { AuthState.ValidationError("All fields are required") }
            return false
        }

        // Check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.update { AuthState.ValidationError("Invalid email address") }
            return false
        }

        // Check if password meets criteria: at least 8 characters, contains uppercase, and a digit
        if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
            _authState.update { AuthState.ValidationError("Password must be at least 8 characters long, contain a number, and an uppercase letter") }
            return false
        }

        // Check if password and confirmPassword match
        if (password != confirmPassword) {
            _authState.update { AuthState.ValidationError("Passwords do not match") }
            return false
        }

        return true
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(
                    authRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication)
                        .container.authRepository
                )
            }
        }
    }
}