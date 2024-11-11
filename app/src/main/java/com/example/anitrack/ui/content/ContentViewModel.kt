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
import com.example.anitrack.model.Content
import com.example.anitrack.network.DatabaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContentViewModel(
    val jikanRepository: JikanRepository,
    val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val contentId = MutableStateFlow<Int?>(null)
    var content = MutableStateFlow<Content?>(null)
        private set

    init {
        viewModelScope.launch {
            contentId.collect {
                getContent(it)
            }
        }
    }

    fun updateContentId(newContentId: Int){
        contentId.value = newContentId
    }


    private suspend fun getContent(it: Int?) {
        it?.let {
            val databaseContent = getFromDatabase(it)
            val contentFromApi = databaseContent ?: getFromApi(it)
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

    private suspend fun getFromApi(id: Int): Content? {
        return jikanRepository.getAnimeById(id).data
    }

    private suspend fun getFromDatabase(id : Int): Content? {
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