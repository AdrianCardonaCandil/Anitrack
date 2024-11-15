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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListsViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _userContentList = MutableStateFlow<List<Content>>(emptyList())
    val userContentList: StateFlow<List<Content>> = _userContentList

    fun loadUserContents(tabIndex: Int, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userContentList.emit(emptyList())

            val userResult = databaseRepository.readDocument(
                DatabaseCollections.Users,
                User::class.java,
                userId
            )

            if (userResult is DatabaseResult.Success) {
                val user = userResult.data
                val contentIds = when (tabIndex) {
                    0 -> user?.watching ?: emptyList()
                    1 -> user?.completed ?: emptyList()
                    2 -> user?.planToWatch ?: emptyList()
                    3 -> user?.favorites ?: emptyList()
                    else -> emptyList()
                }

                if (contentIds.isNotEmpty()) {
                    val contentsResult = databaseRepository.readDocuments(
                        DatabaseCollections.Contents,
                        Content::class.java,
                        contentIds
                    )
                    if (contentsResult is DatabaseResult.Success) {
                        _userContentList.emit(contentsResult.data)
                    } else {
                        _userContentList.emit(emptyList())
                    }
                } else {
                    _userContentList.emit(emptyList())
                }
            } else {
                _userContentList.emit(emptyList())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AnitrackApplication
                ListsViewModel(
                    databaseRepository = application.container.databaseRepository
                )
            }
        }
    }
}


