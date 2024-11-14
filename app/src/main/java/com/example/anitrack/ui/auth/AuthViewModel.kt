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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow(firebaseAuth.currentUser != null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    // Initialize authStateListener directly
    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        _isLoggedIn.value = auth.currentUser != null
    }

    fun resetAuthState() {
        _authState.update { AuthState.Idle }
    }

    init {
        // Add the authStateListener to monitor authentication state
        firebaseAuth.addAuthStateListener(authStateListener)
    }



    // Remove the listener when ViewModel is cleared to prevent memory leaks
    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun signOut() {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {
            val result = authRepository.signOut()
            _authState.update { result }
            if (result is AuthState.Success) {
                _isLoggedIn.update { false }
            }
        }
    }
    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (validateSignUp(username, email, password, confirmPassword)) {
            _authState.update { AuthState.Loading() }
            viewModelScope.launch {
                val userExists = checkUsernameTaken(username)
                val emailExists = checkEmailTaken(email)

                if (userExists) {
                    _authState.update { AuthState.ValidationError("Username already taken") }
                } else if (emailExists) {
                    _authState.update { AuthState.ValidationError("Email already registered") }
                } else {
                    val authResult = authRepository.signUp(email, password)
                    if (authResult is AuthState.Success) {
                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        if (userId != null) {
                            val firestoreResult = createUser(userId, username, email)
                            _authState.update { firestoreResult }
                            if (firestoreResult is AuthState.Success) {
                                _isLoggedIn.update { true }
                            }
                        } else {
                            _authState.update { AuthState.Error(Exception("Failed to retrieve user ID after sign up")) }
                        }
                    } else if (authResult is AuthState.Error) {
                        _authState.update { authResult }
                    }
                }
            }
        } else {
            _authState.update { AuthState.ValidationError("Invalid input data") }
        }
    }

    private suspend fun checkUsernameTaken(username: String): Boolean {
        val result = databaseRepository.filterCollection(
            collectionPath = DatabaseCollections.Users,
            fieldName = "username",
            value = username,
            operation = DatabaseService.ComparisonType.Equals,
            model = User::class.java
        )
        return when (result) {
            is DatabaseResult.Success -> result.data.isNotEmpty()
            is DatabaseResult.Failure -> {
                _authState.update { AuthState.Error(result.error) }
                false
            }
        }
    }

    private suspend fun checkEmailTaken(email: String): Boolean {
        val result = databaseRepository.filterCollection(
            collectionPath = DatabaseCollections.Users,
            fieldName = "email",
            value = email,
            operation = DatabaseService.ComparisonType.Equals,
            model = User::class.java
        )
        return when (result) {
            is DatabaseResult.Success -> result.data.isNotEmpty()
            is DatabaseResult.Failure -> {
                _authState.update { AuthState.Error(result.error) }
                false
            }
        }
    }

    private suspend fun createUser(userId: String, username: String, email: String): AuthState {
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

        return when (result) {
            is DatabaseResult.Success -> {
                println("User created successfully with ID: $userId")
                AuthState.Success
            }
            is DatabaseResult.Failure -> {
                println("Failed to create user document: ${result.error.message}")
                AuthState.Error(result.error)
            }
        }
    }


    fun signIn(username: String, password: String) {
        _authState.update { AuthState.Loading() }
        viewModelScope.launch {

            val emailResult = getEmailByUsername(username)

            if (emailResult is DatabaseResult.Success && emailResult.data != null) {
                val email = emailResult.data
                println("Email: $email")
                val authResult = authRepository.signIn(email, password)

                if (authResult is AuthState.Success) {
                    _isLoggedIn.update { true }
                    _authState.update { AuthState.Success }
                } else if (authResult is AuthState.Error) {
                    _authState.update { AuthState.ValidationError("User not found or incorrect credentials") }
                }
            } else {
                _authState.update { AuthState.ValidationError("Username not found") }
            }
        }
    }
    private suspend fun getEmailByUsername(username: String): DatabaseResult<String?> {
        val result = databaseRepository.filterCollection(
            collectionPath = DatabaseCollections.Users,
            fieldName = "username",
            value = username,
            operation = DatabaseService.ComparisonType.Equals,
            model = User::class.java
        )

        return when (result) {
            is DatabaseResult.Success -> {
                val user = result.data.firstOrNull()
                if (user != null) {
                    DatabaseResult.Success(user.email)
                } else {
                    DatabaseResult.Success(null)
                }
            }
            is DatabaseResult.Failure -> DatabaseResult.Failure(result.error)
        }
    }


    fun setAuthError(authState: AuthState.Error) {
        _authState.update { authState }
    }

    private fun validateSignUp(username: String, email: String, password: String, confirmPassword: String): Boolean {
        if (username.length < 2) {
            _authState.update { AuthState.ValidationError("Username must be at least 2 characters long") }
            return false
        }
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _authState.update { AuthState.ValidationError("All fields are required") }
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.update { AuthState.ValidationError("Invalid email address") }
            return false
        }
        if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
            _authState.update { AuthState.ValidationError("Password must be at least 8 characters long, contain a number, and an uppercase letter") }
            return false
        }
        if (password != confirmPassword) {
            _authState.update { AuthState.ValidationError("Passwords do not match") }
            return false
        }
        return true
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
