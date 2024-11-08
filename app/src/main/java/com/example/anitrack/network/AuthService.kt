package com.example.anitrack.network

import com.example.anitrack.model.User
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth

sealed interface AuthResult {
    object Success : AuthResult
    data class Failure(val error: Exception) : AuthResult
}
sealed class AuthState {
    object Idle : AuthState()                  // Estado inicial sin ninguna operación en curso
    object Success : AuthState()               // La autenticación fue exitosa
    data class Loading(val message: String = "Loading...") : AuthState()  // La autenticación está en proceso
    data class Error(val exception: Throwable) : AuthState()  // Ocurrió un error en la autenticación
    data class ValidationError(val message: String) : AuthState()  // Error en los datos de entrada (ej. email inválido)
}

interface AuthService {
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun removeUser(): AuthResult
    suspend fun validateSignIn(username: String, password: String): AuthResult
}

class FirebaseAuthService(
    private val auth: FirebaseAuth,
    private val firestoreService: DatabaseService  // Inject DatabaseService for Firestore operations
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
            // Use DatabaseService to find the document with the specified username
            val result = firestoreService.filterCollection(
                collectionPath = "users",
                fieldName = "username",
                value = username,
                operation = DatabaseService.ComparisonType.Equals,
                model = User::class.java
            )

            // Process the DatabaseResult
            when (result) {
                is DatabaseResult.Success -> {
                    val user = result.data.firstOrNull()
                    if (user == null) {
                        AuthResult.Failure(Exception("User not found"))
                    } else {
                        // Get the email associated with the username and attempt sign-in with password
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
}
