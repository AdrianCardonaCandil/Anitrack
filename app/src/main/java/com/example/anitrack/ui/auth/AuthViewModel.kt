package com.example.anitrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.AuthRepository
import com.example.anitrack.network.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (validateSignUp(username, email, password, confirmPassword)) {
            viewModelScope.launch {
                val result = authRepository.signUp(email, password)
                _authState.value = result
            }
        } else {
            _authState.value = AuthState.ValidationError("Invalid input data")
        }
    }

    fun signIn(username: String, password: String) {
        viewModelScope.launch {
            // Call validateSignIn in the repository to check credentials
            val result = authRepository.validateSignIn(username, password)
            _authState.value = result
        }
    }

    fun removeUser() {
        viewModelScope.launch {
            val result = authRepository.removeUser()
            _authState.value = result
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(
                    authRepository = (this[APPLICATION_KEY] as AnitrackApplication)
                        .container.authRepository
                )
            }
        }
    }
}
