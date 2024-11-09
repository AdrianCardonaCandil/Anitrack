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
                val result = authRepository.signUp(email, password)
                _authState.update { result }
                if (result is AuthState.Success) {
                    _isLoggedIn.update { true }
                }
            }
        } else {
            _authState.update { AuthState.ValidationError("Invalid input data") }
        }
    }

    fun signIn(username: String, password: String) {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val result = authRepository.validateSignIn(username, password)
            _authState.update { result }

            if (result is AuthState.Success) {
                _isLoggedIn.update { true }
            }
        }
    }

    fun setAuthError(authState: AuthState.Error) {
        _authState.update { authState }
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
