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

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _userProfile = MutableStateFlow<DatabaseResult<User?>?>(null)
    val userProfile: StateFlow<DatabaseResult<User?>?> = _userProfile.asStateFlow()

    private val _userContentList = MutableStateFlow<DatabaseResult<List<Content>>>(DatabaseResult.Success(emptyList()))
    val userContentList: StateFlow<DatabaseResult<List<Content>>> = _userContentList.asStateFlow()

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
                loadUserFavorites(result.data)
            } else {
                _userProfile.value = result
            }
        }
    }

    // Function to load contents associated only with user's favorites
    private fun loadUserFavorites(user: User?) {
        viewModelScope.launch {
            if (user == null) {
                _userContentList.value = DatabaseResult.Failure(Exception("User data not found"))
                return@launch
            }

            // Usa solo los IDs de los contenidos en la lista de favoritos del usuario
            val favoriteContentIds = user.favorites.distinct()

            if (favoriteContentIds.isEmpty()) {
                // Si no hay favoritos, establece la lista de contenido como vacía
                _userContentList.value = DatabaseResult.Success(emptyList())
                return@launch
            }

            // Cargar contenidos favoritos desde la base de datos
            val contents = favoriteContentIds.mapNotNull { contentId ->
                val contentResult = databaseRepository.readDocument(
                    collectionPath = DatabaseCollections.Contents,
                    model = Content::class.java,
                    documentId = contentId
                )
                if (contentResult is DatabaseResult.Success) {
                    contentResult.data
                } else {
                    null
                }
            }

            // Actualizar el estado con la lista de contenidos obtenidos
            _userContentList.value = DatabaseResult.Success(contents)
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
