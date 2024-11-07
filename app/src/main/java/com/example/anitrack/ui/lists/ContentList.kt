package com.example.anitrack.ui.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anitrack.ui.global.ContentCard

@Composable
fun ContentList(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
) {
    // Replace this with real data logic based on selectedTabIndex
    val sampleContentList = getSampleContentList()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(sampleContentList) { content ->
            ContentCard(
                contentTitle = content.title,
                userContentEpisodes = content.userEpisodes,
                totalContentEpisodes = content.totalEpisodes,
                contentType = content.type,
                contentImageUrl = content.imageUrl,
                contentGenres = content.genres,
                showEpisodes = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            )
        }
    }
}

fun getSampleContentList(): List<ContentData> {
    // Replace this with actual data fetching logic
    return listOf(
        ContentData("Title 1", 5, 12, "Anime", "https://example.com/image1.jpg", listOf("Action", "Adventure")),
        ContentData("Title 2", 10, 20, "Anime", "https://example.com/image2.jpg", listOf("Comedy", "Drama")),
        ContentData("Title 3", 3, 10, "Anime", "https://example.com/image3.jpg", listOf("Fantasy", "Magic")),
        ContentData("Title 4", 8, 24, "Anime", "https://example.com/image4.jpg", listOf("Mystery", "Thriller")),
        ContentData("Title 5", 15, 30, "Anime", "https://example.com/image5.jpg", listOf("Sci-Fi", "Supernatural"))
    )
}

data class ContentData(
    val title: String,
    val userEpisodes: Int,
    val totalEpisodes: Int,
    val type: String,
    val imageUrl: String,
    val genres: List<String>
)