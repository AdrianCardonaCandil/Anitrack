package com.example.anitrack.data

import com.example.anitrack.network.AuthResult
import com.example.anitrack.network.AuthService
import com.example.anitrack.network.AuthState

interface AuthRepository {

    suspend fun signIn(email: String, password: String): AuthState
    suspend fun signUp(email: String, password: String): AuthState
    suspend fun signOut(): AuthState
    suspend fun deleteAccount(): AuthState
    suspend fun updateEmail(newEmail: String): AuthState
    suspend fun updatePassword(newPassword: String): AuthState
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
    override suspend fun signOut(): AuthState {
        return when (val result = authService.signOut()) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun deleteAccount(): AuthState {
        return when (val result = authService.deleteAccount()) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun updateEmail(newEmail: String): AuthState {
        return when (val result = authService.updateEmail(newEmail)) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }

    override suspend fun updatePassword(newPassword: String): AuthState {
        return when (val result = authService.updatePassword(newPassword)) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Failure -> AuthState.Error(result.error)
        }
    }
}

