package com.example.anitrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onGridContentClicked: (id: Int) -> Unit,
    modifier: Modifier = Modifier
){
    val gridContentLists = listOf(
        homeViewModel.currentSeasonList.collectAsState(),
        homeViewModel.upcomingSeasonList.collectAsState(),
        homeViewModel.topAnimeList.collectAsState()
    )
    Column(modifier) {
        Banner(modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 475.dp,
                max = 500.dp
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(15.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        gridContentLists.forEachIndexed { index, value ->
            ContentGrid(
                onCardClicked = { onGridContentClicked(it) },
                gridName = stringResource(homeViewModel.contentGridNameResources[index]),
                contentList = value.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    }
}