package com.example.anitrack.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.global.ContentCard

@Composable
fun SearchScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        SearchBar(modifier = Modifier.padding(15.dp))
        ContentCard(
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
        ContentCard(
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
        ContentCard(
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
    }

}