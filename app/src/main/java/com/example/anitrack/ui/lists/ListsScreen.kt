package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.anitrack.ui.global.TopAppBar

@Composable
fun ListsScreen(
    modifier: Modifier = Modifier
) {
    // State to track the selected tab index
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Get the user list based on the selected tab

    Column(modifier = modifier.fillMaxSize()) {

        // Tab Row
        ListsTabRow(selectedTabIndex) { newIndex ->
            selectedTabIndex = newIndex
        }

        // List Header with item count
        ListHeader(
            selectedTabIndex = selectedTabIndex,
            itemCount = 0 // Pass the number of items in the list
        )

        // Content List
        ContentList(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxSize()
        )
    }
}
