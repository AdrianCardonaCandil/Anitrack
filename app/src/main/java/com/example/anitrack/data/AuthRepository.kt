package com.example.anitrack.data

import com.example.anitrack.network.AuthResult
import com.example.anitrack.network.AuthService
import com.example.anitrack.network.AuthState

interface AuthRepository {
    suspend fun signUp(email: String, password: String): AuthState
    suspend fun signIn(email: String, password: String): AuthState
    suspend fun removeUser(): AuthState
    suspend fun validateSignIn(username: String, password: String): AuthState
    suspend fun isUsernameTaken(username: String): Boolean
    suspend fun isEmailTaken(email: String): Boolean
}

class AuthFirebaseRepository(private val authService: AuthService) : AuthRepository {

    override suspend fun signUp(email: String, password: String): AuthState {
        return when (val result = authService.signUp(email, password)) {
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

    override suspend fun isUsernameTaken(username: String): Boolean {
        return when (val result = authService.isUsernameTaken(username)) {
            is AuthResult.Success -> true
            is AuthResult.Failure -> false
        }
    }

    override suspend fun isEmailTaken(email: String): Boolean {
        return when (val result = authService.isEmailTaken(email)) {
            is AuthResult.Success -> true
            is AuthResult.Failure -> false
        }
    }
}

