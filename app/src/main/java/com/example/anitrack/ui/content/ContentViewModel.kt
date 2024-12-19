package com.example.anitrack.ui.content

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.data.DatabaseRepository
import com.example.anitrack.data.JikanRepository
import com.example.anitrack.model.Character
import com.example.anitrack.model.CharacterList
import com.example.anitrack.model.Content
import com.example.anitrack.model.User
import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.ui.lists.ListHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentViewModel(
    val jikanRepository: JikanRepository,
    val databaseRepository: DatabaseRepository
) : ViewModel() {

    var showEditDialog by mutableStateOf(false)
        private set
    private val contentId = MutableStateFlow<Int?>(null)
    var content = MutableStateFlow<Content?>(null)
        private set
    var characters = MutableStateFlow<List<Character>?>(null)
        private set

    private val listHandler = ListHandler(databaseRepository)
    var contentListsState = mutableStateOf(emptyList<ListHandler.ListType>())
        private set

    init {
        viewModelScope.launch {
            contentId.collect {
                withContext(Dispatchers.IO) {
                    launch { getContent(it) }
                    launch { getCharacters(it) }
                }
            }
        }
    }

    private suspend fun getCharacters(it: Int?) {
        it?.let {
            val databaseCharacters = getCharactersFromDatabase(it)
            Log.d("contentViewModel", databaseCharacters.toString())
            val charactersFromApi = databaseCharacters ?: getCharactersFromApi(it)
            characters.value = charactersFromApi
            if (databaseCharacters == null && charactersFromApi != null){
                storeCharacters(it, charactersFromApi)
            }
        }
    }

    private suspend fun storeCharacters(id: Int, charactersFromApi: List<Character>) {
        databaseRepository.createDocument(
            collectionPath = DatabaseCollections.Characters,
            data = CharacterList(data = charactersFromApi),
            documentId = id.toString()
        )
    }

    private suspend fun getCharactersFromDatabase(id: Int): List<Character>? {
        val databaseResult = databaseRepository.readDocument(
            collectionPath = DatabaseCollections.Characters,
            model = CharacterList::class.java,
            documentId = id.toString()
        )
        return when (databaseResult) {
            is DatabaseResult.Success -> databaseResult.data?.data
            is DatabaseResult.Failure -> {
                Log.d("contentViewModel", databaseResult.error.toString())
                null
            }
        }
    }

    private suspend fun getCharactersFromApi(id: Int): List<Character>? {
        return jikanRepository.getAnimeCharacters(id).data
    }

    fun updateContentId(newContentId: Int){
        contentId.value = newContentId
    }
    
    
    private suspend fun getContent(it: Int?) {
        it?.let {
            val databaseContent = getContentFromDatabase(it)
            val contentFromApi = databaseContent ?: getContentFromApi(it)
            content.value = contentFromApi
            if (databaseContent == null && contentFromApi != null) {
                storeContent(it, contentFromApi)
            }
        }
    }

    private suspend fun storeContent(id: Int, content: Content?) {
        databaseRepository.createDocument(
            collectionPath = DatabaseCollections.Contents,
            data = content,
            documentId =  content?.id.toString()
        )
    }

    private suspend fun getContentFromApi(id: Int): Content? {
        return jikanRepository.getAnimeById(id).data
    }

    private suspend fun getContentFromDatabase(id : Int): Content? {
        val databaseResult = databaseRepository.readDocument(
            collectionPath = DatabaseCollections.Contents,
            model = Content::class.java,
            documentId = id.toString()
        )
        return when (databaseResult) {
            is DatabaseResult.Success -> databaseResult.data
            is DatabaseResult.Failure -> {
                Log.d("contentViewModel", databaseResult.error.toString())
                null
            }
        }
    }

    fun changeDialogVisibility(show: Boolean) {
        showEditDialog = show
    }

    fun addToList(userId: String, contentId: String, listType: ListHandler.ListType) {
        viewModelScope.launch {
            val result = listHandler.addToList(userId, contentId, listType)
            if (result is DatabaseResult.Success) {
                updateContentListsState(userId, contentId)
            }
        }
    }

    fun removeFromList(userId: String, contentId: String, listType: ListHandler.ListType) {
        viewModelScope.launch {
            val result = listHandler.removeFromList(userId, contentId, listType)
            if (result is DatabaseResult.Success) {
                updateContentListsState(userId, contentId)
            }
        }
    }

    fun updateContentListsState(userId: String, contentId: String) {
        viewModelScope.launch {
            val userResult = databaseRepository.readDocument(
                collectionPath = DatabaseCollections.Users,
                model = User::class.java,
                documentId = userId
            )
            if (userResult is DatabaseResult.Success) {
                val user = userResult.data
                contentListsState.value = listOfNotNull(
                    if (user?.watching?.contains(contentId) == true) ListHandler.ListType.WATCHING else null,
                    if (user?.completed?.contains(contentId) == true) ListHandler.ListType.COMPLETED else null,
                    if (user?.planToWatch?.contains(contentId) == true) ListHandler.ListType.PLAN_TO_WATCH else null,
                    if (user?.favorites?.contains(contentId) == true) ListHandler.ListType.FAVORITES else null
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ContentViewModel(
                    jikanRepository = (this[APPLICATION_KEY] as AnitrackApplication)
                        .container
                        .jikanRepository,
                    databaseRepository = (this[APPLICATION_KEY] as AnitrackApplication)
                        .container
                        .databaseRepository
                )
            }
        }
    }
}