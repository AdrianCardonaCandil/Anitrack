package com.example.anitrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.AuthRepository
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.data.DatabaseRepository
import com.example.anitrack.model.User
import com.example.anitrack.network.AuthState
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.network.DatabaseService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _userId = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.uid)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        observeAuthState()
    }
    private fun observeAuthState() {
        firebaseAuth.addAuthStateListener { auth ->
            viewModelScope.launch {
                _userId.value = auth.currentUser?.uid
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener { auth ->
            _userId.value = auth.currentUser?.uid
        }
    }

    fun resetAuthState() {
        _authState.update { AuthState.Idle }
    }

    fun signOut() {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val result = authRepository.signOut()
            _authState.update { result }
            if (result is AuthState.Success) {
                _userId.value = null
            }
        }
    }

    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (!validateSignUp(username, email, password, confirmPassword)) return

        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val usernameExists = isFieldTaken(DatabaseCollections.Users, "username", username)
            val emailExists = isFieldTaken(DatabaseCollections.Users, "email", email)

            when {
                usernameExists -> _authState.update { AuthState.ValidationError("Username already taken") }
                emailExists -> _authState.update { AuthState.ValidationError("Email already registered") }
                else -> {
                    val authResult = authRepository.signUp(email, password)
                    if (authResult is AuthState.Success) {
                        val currentUserId = firebaseAuth.currentUser?.uid
                        if (currentUserId != null) {
                            val creationResult = createUserDocument(currentUserId, username, email)
                            _authState.update { creationResult }
                        } else {
                            _authState.update { AuthState.Error(Exception("User ID retrieval failed")) }
                        }
                    } else {
                        _authState.update { authResult }
                    }
                }
            }
        }
    }

    private suspend fun isFieldTaken(collection: DatabaseCollections, field: String, value: String): Boolean {
        val result = databaseRepository.filterCollection(
            collectionPath = collection,
            fieldName = field,
            value = value,
            operation = DatabaseService.ComparisonType.Equals,
            model = User::class.java
        )
        return result is DatabaseResult.Success && result.data.isNotEmpty()
    }

    private suspend fun createUserDocument(userId: String, username: String, email: String): AuthState {
        val user = User(
            id = userId,
            username = username,
            email = email,
            createdAt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())
        )
        val result = databaseRepository.createDocument(
            collectionPath = DatabaseCollections.Users,
            data = user,
            documentId = userId
        )
        return if (result is DatabaseResult.Success) {
            AuthState.Success
        } else {
            AuthState.Error((result as DatabaseResult.Failure).error)
        }
    }

    fun signIn(username: String, password: String) {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val emailResult = fetchEmailByUsername(username)
            if (emailResult is DatabaseResult.Success && emailResult.data != null) {
                val email = emailResult.data
                val authResult = authRepository.signIn(email, password)
                if (authResult is AuthState.Success) {
                    _authState.update { AuthState.Success }
                } else {
                    _authState.update { AuthState.ValidationError("Invalid credentials") }
                }
            } else {
                _authState.update { AuthState.ValidationError("Username not found") }
            }
        }
    }

    private suspend fun fetchEmailByUsername(username: String): DatabaseResult<String?> {
        val result = databaseRepository.filterCollection(
            collectionPath = DatabaseCollections.Users,
            fieldName = "username",
            value = username,
            operation = DatabaseService.ComparisonType.Equals,
            model = User::class.java
        )
        return if (result is DatabaseResult.Success) {
            DatabaseResult.Success(result.data.firstOrNull()?.email)
        } else {
            result as DatabaseResult.Failure
        }
    }

    private fun validateSignUp(username: String, email: String, password: String, confirmPassword: String): Boolean {
        return when {
            username.length < 2 -> {
                _authState.update { AuthState.ValidationError("Username must be at least 2 characters") }
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _authState.update { AuthState.ValidationError("Invalid email format") }
                false
            }
            password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() } -> {
                _authState.update { AuthState.ValidationError("Password must be at least 8 characters, include a number, and an uppercase letter") }
                false
            }
            password != confirmPassword -> {
                _authState.update { AuthState.ValidationError("Passwords do not match") }
                false
            }
            else -> true
        }
    }
    fun deleteAccount() {
        viewModelScope.launch {
            val result = databaseRepository.deleteDocument(DatabaseCollections.Users,
                userId.toString()
            )
            if (result is DatabaseResult.Success) {
                authRepository.deleteAccount()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication
                AuthViewModel(
                    authRepository = app.container.authRepository,
                    databaseRepository = app.container.databaseRepository
                )
            }
        }
    }
}
