package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.model.Content
import com.example.anitrack.ui.global.ContentCard

@Composable
fun ContentList(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
) {
    val sampleContentList = getSampleContentList()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(sampleContentList) { contentData ->
            ContentCard(
                content = contentData.toContent(),
                userContentEpisodes = contentData.userEpisodes,
                onCardClicked = { /* Implementa la lógica de click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            )
        }
    }
}

// Agregar una función de extensión para convertir ContentData a Content
fun ContentData.toContent(): Content {
    return Content(
        id = this.id,
        title = this.title,
        coverImage = this.imageUrl,
        episodes = this.totalEpisodes,
        contentGenres = this.genres,
        type = this.type
    )
}

data class ContentData(
    val id: Int, // este campo es necesario si `Content` requiere un ID
    val title: String,
    val userEpisodes: Int,
    val totalEpisodes: Int,
    val type: String,
    val imageUrl: String,
    val genres: List<String>
)

fun getSampleContentList(): List<ContentData> {
    return listOf(
        ContentData(1, "Title 1", 5, 12, "Anime", "https://example.com/image1.jpg", listOf("Action", "Adventure")),
        ContentData(2, "Title 2", 10, 20, "Anime", "https://example.com/image2.jpg", listOf("Comedy", "Drama")),
        ContentData(3, "Title 3", 3, 10, "Anime", "https://example.com/image3.jpg", listOf("Fantasy", "Magic")),
        ContentData(4, "Title 4", 8, 24, "Anime", "https://example.com/image4.jpg", listOf("Mystery", "Thriller")),
        ContentData(5, "Title 5", 15, 30, "Anime", "https://example.com/image5.jpg", listOf("Sci-Fi", "Supernatural"))
    )
}
