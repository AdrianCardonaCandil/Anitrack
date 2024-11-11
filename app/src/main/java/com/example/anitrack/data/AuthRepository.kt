package com.example.anitrack.data

import com.example.anitrack.network.AuthResult
import com.example.anitrack.network.AuthService
import com.example.anitrack.network.AuthState

interface AuthRepository {

    suspend fun signIn(email: String, password: String): AuthState
    suspend fun removeUser(): AuthState
    suspend fun validateSignIn(username: String, password: String): AuthState

    suspend fun signUp(email: String, password: String): AuthState
}

class AuthFirebaseRepository(private val authService: AuthService) : AuthRepository {

    override suspend fun signUp(email: String, password: String): AuthState {
        return when (val result = authService.signUp(email, password,)) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthState {
        return when (val result = authService.signIn(email, password)) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun removeUser(): AuthState {
        return when (val result = authService.removeUser()) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun validateSignIn(username: String, password: String): AuthState {
        return when (val result = authService.validateSignIn(username, password)) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

}

