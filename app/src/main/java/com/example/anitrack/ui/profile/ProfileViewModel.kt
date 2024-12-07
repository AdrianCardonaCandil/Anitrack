package com.example.anitrack.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.AuthRepository
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.data.DatabaseRepository
import com.example.anitrack.model.Content
import com.example.anitrack.model.User
import com.example.anitrack.network.AuthState
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.network.DatabaseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<DatabaseResult<User?>?>(null)
    val userProfile: StateFlow<DatabaseResult<User?>?> = _userProfile.asStateFlow()

    private val _userContentList = MutableStateFlow<DatabaseResult<List<Content>>>(DatabaseResult.Success(emptyList()))
    val userContentList: StateFlow<DatabaseResult<List<Content>>> = _userContentList.asStateFlow()

    private val _profileEditState = MutableStateFlow<AuthState>(AuthState.Idle)
    val profileEditState: StateFlow<AuthState> = _profileEditState.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun loadUserProfileAndFavorites(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userResult = databaseRepository.readDocument(
                collectionPath = DatabaseCollections.Users,
                model = User::class.java,
                documentId = userId
            )

            if (userResult is DatabaseResult.Success) {
                val user = userResult.data
                val favoriteContentIds = user?.favorites.orEmpty().distinct()

                val contentResult = if (favoriteContentIds.isNotEmpty()) {
                    databaseRepository.readDocuments(
                        collectionPath = DatabaseCollections.Contents,
                        model = Content::class.java,
                        documentIds = favoriteContentIds
                    )
                } else {
                    DatabaseResult.Success(emptyList())
                }

                withContext(Dispatchers.Main) {
                    _userProfile.value = userResult
                    _userContentList.value = contentResult
                }
            } else {
                withContext(Dispatchers.Main) {
                    _userProfile.value = userResult
                }
            }
        }
    }

    fun resetProfileEditState() {
        _profileEditState.value = AuthState.Idle
    }

    fun updateUserDetails(
        userId: String,
        currentEmail: String,
        currentUsername: String,
        newUsername: String,
        newEmail: String,
        newDescription: String?
    ) {
        viewModelScope.launch {
            _profileEditState.value = AuthState.Loading()

            val trimmedUsername = newUsername.trim()
            val trimmedEmail = newEmail.trim()

            // Validations
            if (trimmedUsername.length < 2) {
                _profileEditState.value = AuthState.ValidationError("Username must be at least 2 characters")
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                _profileEditState.value = AuthState.ValidationError("Invalid email format")
                return@launch
            }

            // Check username availability if changed
            if (trimmedUsername != currentUsername) {
                val usernameExists = isFieldTaken(DatabaseCollections.Users, "username", trimmedUsername)
                if (usernameExists) {
                    _profileEditState.value = AuthState.ValidationError("Username already taken")
                    return@launch
                }
            }

            // Check email availability if changed
            if (trimmedEmail != currentEmail) {
                val emailExists = isFieldTaken(DatabaseCollections.Users, "email", trimmedEmail)
                if (emailExists) {
                    _profileEditState.value = AuthState.ValidationError("Email already registered")
                    return@launch
                }
            }

            val updates = mutableMapOf<String, Any>(
                "username" to trimmedUsername,
                "email" to trimmedEmail
            )
            newDescription?.let {
                updates["description"] = it.trim()
            }

            val updateResult = databaseRepository.updateDocument(
                collectionPath = DatabaseCollections.Users,
                documentId = userId,
                updates = updates
            )

            if (updateResult is DatabaseResult.Success) {
                // Update email in Auth if changed
                if (trimmedEmail != currentEmail) {
                    val emailUpdateResult = authRepositoryUpdateEmail(trimmedEmail)
                    if (emailUpdateResult !is AuthState.Success) {
                        _profileEditState.value = emailUpdateResult
                        return@launch
                    }
                }
                _profileEditState.value = AuthState.Success
            } else {
                _profileEditState.value = AuthState.Error((updateResult as DatabaseResult.Failure).error)
            }
        }
    }

    fun updateUserPassword(newPassword: String, repeatPassword: String) {
        viewModelScope.launch {
            _profileEditState.value = AuthState.Loading()

            // Validate password
            if (newPassword.length < 8 || !newPassword.any { it.isDigit() } || !newPassword.any { it.isUpperCase() }) {
                _profileEditState.value = AuthState.ValidationError("Password must be at least 8 characters, include a number, and an uppercase letter")
                return@launch
            }

            if (newPassword != repeatPassword) {
                _profileEditState.value = AuthState.ValidationError("Passwords do not match")
                return@launch
            }

            val result = authRepositoryUpdatePassword(newPassword)
            _profileEditState.value = result
        }
    }

    fun updateUserProfilePicture(userId: String, imageUrl: String) {
        viewModelScope.launch {
            _profileEditState.value = AuthState.Loading()
            val updateResult = databaseRepository.updateDocument(
                collectionPath = DatabaseCollections.Users,
                documentId = userId,
                updates = mapOf("profilePicture" to imageUrl)
            )

            if (updateResult is DatabaseResult.Success) {
                _profileEditState.value = AuthState.Success
            } else {
                _profileEditState.value = AuthState.Error((updateResult as DatabaseResult.Failure).error)
            }
        }
    }

    fun deleteAccount(userId: String) {
        viewModelScope.launch {
            _profileEditState.value = AuthState.Loading()
            val result = databaseRepository.deleteDocument(DatabaseCollections.Users, userId)
            if (result is DatabaseResult.Success) {
                val authRes = authRepository.deleteAccount()
                _profileEditState.value = authRes
            } else {
                _profileEditState.value = AuthState.Error((result as DatabaseResult.Failure).error)
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

    private suspend fun authRepositoryUpdateEmail(newEmail: String): AuthState {
        return try {
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.updateEmail(newEmail)?.await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e)
        }
    }

    private suspend fun authRepositoryUpdatePassword(newPassword: String): AuthState {
        return try {
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.updatePassword(newPassword)?.await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication
                ProfileViewModel(
                    databaseRepository = application.container.databaseRepository,
                    authRepository = application.container.authRepository
                )
            }
        }
    }
}
