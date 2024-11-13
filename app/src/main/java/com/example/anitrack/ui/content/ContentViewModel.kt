package com.example.anitrack.ui.content

import android.util.Log
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
import com.example.anitrack.network.DatabaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentViewModel(
    val jikanRepository: JikanRepository,
    val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val contentId = MutableStateFlow<Int?>(null)
    var content = MutableStateFlow<Content?>(null)
        private set
    var characters = MutableStateFlow<List<Character>?>(null)
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
            Log.d("contentViewModel", databaseContent.toString())
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