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
    suspend fun removeUser(): AuthResult
    suspend fun validateSignIn(username: String, password: String): AuthResult
    suspend fun isUsernameTaken(username: String): AuthResult
    suspend fun isEmailTaken(email: String): AuthResult
}

class FirebaseAuthService(
    private val auth: FirebaseAuth,
    private val firestoreService: DatabaseService
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

    override suspend fun removeUser(): AuthResult {
        return try {
            auth.currentUser?.delete()?.await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

    override suspend fun validateSignIn(username: String, password: String): AuthResult {
        return try {
            val result = firestoreService.filterCollection(
                collectionPath = "users",
                fieldName = "username",
                value = username,
                operation = DatabaseService.ComparisonType.Equals,
                model = User::class.java
            )

            when (result) {
                is DatabaseResult.Success -> {
                    val user = result.data.firstOrNull()
                    if (user == null) {
                        AuthResult.Failure(Exception("User not found"))
                    } else {
                        val email = user.email
                        try {
                            auth.signInWithEmailAndPassword(email, password).await()
                            AuthResult.Success
                        } catch (e: Exception) {
                            AuthResult.Failure(Exception("Incorrect password"))
                        }
                    }
                }
                is DatabaseResult.Failure -> AuthResult.Failure(result.error)
            }
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

    override suspend fun isUsernameTaken(username: String): AuthResult {
        return try {
            val result = firestoreService.filterCollection(
                collectionPath = "users",
                fieldName = "username",
                value = username,
                operation = DatabaseService.ComparisonType.Equals,
                model = User::class.java
            )

            if (result is DatabaseResult.Success && result.data.isNotEmpty()) {
                AuthResult.Success
            } else {
                AuthResult.Failure(Exception("Username is available"))
            }
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }

    override suspend fun isEmailTaken(email: String): AuthResult {
        return try {
            val result = firestoreService.filterCollection(
                collectionPath = "users",
                fieldName = "email",
                value = email,
                operation = DatabaseService.ComparisonType.Equals,
                model = User::class.java
            )

            if (result is DatabaseResult.Success && result.data.isNotEmpty()) {
                AuthResult.Success
            } else {
                AuthResult.Failure(Exception("Email is available"))
            }
        } catch (e: Exception) {
            AuthResult.Failure(e)
        }
    }
}
