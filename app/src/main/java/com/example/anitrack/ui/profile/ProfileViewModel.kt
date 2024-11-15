package com.example.anitrack.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.data.DatabaseRepository
import com.example.anitrack.model.Content
import com.example.anitrack.model.User
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.network.DatabaseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<DatabaseResult<User?>?>(null)
    val userProfile: StateFlow<DatabaseResult<User?>?> = _userProfile.asStateFlow()

    private val _userContentList = MutableStateFlow<DatabaseResult<List<Content>>>(DatabaseResult.Success(emptyList()))
    val userContentList: StateFlow<DatabaseResult<List<Content>>> = _userContentList.asStateFlow()

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            val result = databaseRepository.readDocument(
                collectionPath = DatabaseCollections.Users,
                model = User::class.java,
                documentId = userId
            )

            if (result is DatabaseResult.Success) {
                _userProfile.value = result
                loadUserFavorites(result.data)
            } else {
                _userProfile.value = result
            }
        }
    }

    private fun loadUserFavorites(user: User?) {
        viewModelScope.launch {
            if (user == null) {
                _userContentList.value = DatabaseResult.Failure(Exception("User data not found"))
                return@launch
            }

            val favoriteContentIds = user.favorites.distinct()
            if (favoriteContentIds.isEmpty()) {
                _userContentList.value = DatabaseResult.Success(emptyList())
                return@launch
            }

            val contentResult = databaseRepository.readDocuments(
                collectionPath = DatabaseCollections.Contents,
                model = Content::class.java,
                documentIds = favoriteContentIds
            )
            _userContentList.value = contentResult
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication
                ProfileViewModel(
                    databaseRepository = application.container.databaseRepository
                )
            }
        }
    }
}
