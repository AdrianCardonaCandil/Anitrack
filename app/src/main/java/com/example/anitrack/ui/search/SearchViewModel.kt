package com.example.anitrack.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.data.JikanRepository
import com.example.anitrack.model.Content
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val jikanRepository: JikanRepository
) : ViewModel() {

    var userInput = MutableStateFlow("")
        private set

    var searchResult: MutableStateFlow<List<Content>?> = MutableStateFlow(null)
        private set

    fun onUserInput(value: String) {
        userInput.value = value
    }

    init {
        viewModelScope.launch {
            delay(2500)
            userInput.debounce(25).collect {
                getSearchResult(it)
            }
        }
    }

    private suspend fun getSearchResult(userInput: String) {
        try {
            searchResult.value = jikanRepository.animeSearch(
                limit = 25,
                page = 1,
                sfw = true,
                q = userInput
            ).data
        } catch (e: Exception) {
            Log.d(
                ":searchViewModel",
                "Exception occurred while retrieving search list"
            )
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    jikanRepository = (this[APPLICATION_KEY] as AnitrackApplication)
                        .container
                        .jikanRepository
                )
            }
        }
    }
}