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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<DatabaseResult<User?>?>(null)
    val userProfile: StateFlow<DatabaseResult<User?>?> = _userProfile.asStateFlow()

    private val _userContentList = MutableStateFlow<DatabaseResult<List<Content>>>(DatabaseResult.Success(emptyList()))
    val userContentList: StateFlow<DatabaseResult<List<Content>>> = _userContentList.asStateFlow()

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
