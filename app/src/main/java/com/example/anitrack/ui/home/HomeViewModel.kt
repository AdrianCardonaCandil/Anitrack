package com.example.anitrack.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anitrack.AnitrackApplication
import com.example.anitrack.R
import com.example.anitrack.data.JikanRepository
import com.example.anitrack.model.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val jikanRepository: JikanRepository) : ViewModel() {

    val contentGridNameResources: List<Int> = listOf(
        R.string.newSeasonContentGridLabel,
        R.string.upcomingContentGridLabel,
        R.string.topAnimeContentGridLabel,
    )

    // Home screen grid content lists state holders declaration
    var currentSeasonList: MutableStateFlow<List<Content>?> = MutableStateFlow(null)
        private set
    var upcomingSeasonList: MutableStateFlow<List<Content>?> = MutableStateFlow(null)
        private set
    var topAnimeList: MutableStateFlow<List<Content>?> = MutableStateFlow(null)
        private set

    init {
        getHomeScreenGridContentLists()
    }

    private fun getHomeScreenGridContentLists() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                launch { getCurrentSeason() }
                launch { getUpcomingSeason() }
                launch { getTopAnime() }
            }
        }
    }

    private suspend fun getCurrentSeason() {
       try {
           currentSeasonList.value = jikanRepository.getSeason(limit = 4).data
       } catch (e: Exception) {
           Log.d(
               ":homeViewModel",
               "Exception occurred while retrieving current season list"
           )
       }
    }

    private suspend fun getUpcomingSeason() {
        try {
            upcomingSeasonList.value = jikanRepository.getSeason(upcoming = true, limit = 4).data
        } catch (e: Exception) {
            Log.d(
                ":homeViewModel",
                "Exception occurred while retrieving upcoming season list"
            )
        }
    }

    private suspend fun getTopAnime() {
        try {
            topAnimeList.value = jikanRepository.getTopAnime(limit = 4).data
        } catch (e: Exception) {
            Log.d(
                ":homeViewModel",
                "Exception occurred while retrieving top anime list"
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(jikanRepository = (this[APPLICATION_KEY] as AnitrackApplication)
                    .container
                    .jikanRepository
                )
            }
        }
    }
}