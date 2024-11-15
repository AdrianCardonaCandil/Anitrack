package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun ListsScreen(
    modifier: Modifier = Modifier,
    viewModel: ListsViewModel = viewModel(),
    onContentClicked: (Int) -> Unit,
    userId: String,
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val contentList by viewModel.userContentList.collectAsState()
    val contentProgress by viewModel.contentProgress.collectAsState() // Obteniendo el progreso

    LaunchedEffect(selectedTabIndex) {
        viewModel.loadUserContents(selectedTabIndex, userId)
    }

    Column(modifier = modifier.fillMaxSize()) {
        ListsTabRow(selectedTabIndex) { newIndex ->
            selectedTabIndex = newIndex
        }

        ListHeader(
            selectedTabIndex = selectedTabIndex,
            itemCount = contentList.size
        )

        ContentList(
            contentList = contentList,
            contentProgress = contentProgress, // Pasando el progreso aquí
            onContentClicked = onContentClicked,
            modifier = Modifier.fillMaxSize(),
            showProgressControls = (selectedTabIndex == 0),
            onEpisodeIncrement = { contentId ->
                val currentEpisodes = contentProgress[contentId.toString()] ?: 0
                viewModel.incrementEpisode(userId, contentId.toString(), currentEpisodes)
            },
            onEpisodeDecrement = { contentId ->
                val currentEpisodes = contentProgress[contentId.toString()] ?: 0
                viewModel.decrementEpisode(userId, contentId.toString(), currentEpisodes)
            }
        )
    }
}



