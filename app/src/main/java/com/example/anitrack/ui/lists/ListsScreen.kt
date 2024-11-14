package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListsScreen(
    viewModel: ListsViewModel = viewModel(),
    onContentClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val contentList by viewModel.userContentList.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

        // Tab Row para seleccionar la lista de contenido
        ListsTabRow(selectedTabIndex) { newIndex ->
            selectedTabIndex = newIndex
            viewModel.loadUserContents(newIndex) // Cargar la lista correspondiente
        }

        // List Header con el conteo de elementos
        ListHeader(
            selectedTabIndex = selectedTabIndex,
            itemCount = contentList.size
        )

        // Mostrar el ContentList con los contenidos actuales
        ContentList(
            contentList = contentList,
            onContentClicked = onContentClicked,
            modifier = Modifier.fillMaxSize()
        )
    }
}
