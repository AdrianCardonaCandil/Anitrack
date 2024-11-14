package com.example.anitrack.ui.lists

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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListsViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _userContentList = MutableStateFlow<List<Content>>(emptyList())
    val userContentList: StateFlow<List<Content>> = _userContentList

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        loadUserContents(0) // Cargar la lista inicial (watching)
    }

    // Función para cargar los contenidos según la pestaña seleccionada
    fun loadUserContents(tabIndex: Int) {
        viewModelScope.launch {
            userId?.let { id ->
                // Obtener el perfil del usuario
                val userResult = databaseRepository.readDocument(DatabaseCollections.Users, User::class.java, id)
                if (userResult is DatabaseResult.Success) {
                    val user = userResult.data
                    val contentIds = when (tabIndex) {
                        0 -> user?.watching ?: emptyList()
                        1 -> user?.completed ?: emptyList()
                        2 -> user?.planToWatch ?: emptyList()
                        3 -> user?.favorites ?: emptyList()
                        else -> emptyList()
                    }

                    // Cargar contenidos según los IDs en la lista seleccionada
                    val contents = contentIds.mapNotNull { contentId ->
                        val contentResult = databaseRepository.readDocument(DatabaseCollections.Contents, Content::class.java, contentId)
                        if (contentResult is DatabaseResult.Success) contentResult.data else null
                    }
                    _userContentList.value = contents
                }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication
                ListsViewModel(
                    databaseRepository = application.container.databaseRepository
                )
            }
        }
    }
}
