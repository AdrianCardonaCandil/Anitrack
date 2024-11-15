package com.example.anitrack.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.global.ContentCard

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onSearchCardClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val searchResult = searchViewModel.searchResult.collectAsState()
    LazyColumn(
        modifier = modifier
    ) {
        item {
            SearchBar(
                currentInput = searchViewModel.userInput.collectAsState().value,
                onUserInput = { searchViewModel.onUserInput(it) },
                modifier = Modifier.padding(15.dp)
            )
        }
        items(searchResult.value ?: listOf()){
            ContentCard(
                content = it,
                onCardClicked = { onSearchCardClicked(it) },
                showEpisodes = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 15.dp,
                        start = 15.dp,
                        end = 15.dp
                    ),
                onEpisodeIncrement = {},
                onEpisodeDecrement = {},
                onMoveToCompleted = {}
            )
        }
    }
}