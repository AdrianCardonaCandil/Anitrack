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
import com.example.anitrack.network.DatabaseService.ComparisonType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _userProfile = MutableStateFlow<DatabaseResult<User?>?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _contentList = MutableStateFlow<DatabaseResult<List<Content>>?>(null)
    val contentList = _contentList.asStateFlow()

    init {
        userId?.let {
            loadUserProfile(it)
        }
    }

    // Function to load user profile data from "Users" collection
    private fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            val result = databaseRepository.readDocument(
                collectionPath = DatabaseCollections.Users,
                model = User::class.java,
                documentId = userId
            )

            if (result is DatabaseResult.Success) {
                _userProfile.value = result
                loadUserContents(result.data)
            }
        }
    }

    // Function to load contents associated with user's lists
    private fun loadUserContents(user: User?) {
        viewModelScope.launch {
            if (user == null) {
                _contentList.value = DatabaseResult.Failure(Exception("User data not found"))
                return@launch
            }

            val allContentIds = user.watching + user.completed + user.planToWatch + user.favorites
            val uniqueContentIds = allContentIds.distinct()

            val result = databaseRepository.filterCollection(
                collectionPath = DatabaseCollections.Contents,
                fieldName = "id",
                value = uniqueContentIds,
                operation = ComparisonType.Equals,
                model = Content::class.java
            )
            _contentList.value = result
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
