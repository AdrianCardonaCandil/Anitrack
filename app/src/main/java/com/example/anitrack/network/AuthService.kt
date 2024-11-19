package com.example.anitrack.network

import com.example.anitrack.model.User
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth


sealed interface AuthResult {
    object Success : AuthResult
    data class Failure(val error: Exception) : AuthResult
}
sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    data class Loading(val message: String = "Loading...") : AuthState()
    data class Error(val exception: Throwable) : AuthState()
    data class ValidationError(val message: String) : AuthState()
}

interface AuthService {
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun deleteAccount(): AuthResult
    suspend fun signOut(): AuthResult
}

class FirebaseAuthService(
    private val auth: FirebaseAuth,
) : AuthService {

    override suspend fun signUp(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }
    override suspend fun signOut(): AuthResult {
        return try {
            auth.signOut()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

    override suspend fun deleteAccount(): AuthResult {
        return try {
            auth.currentUser?.delete()?.await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

}
