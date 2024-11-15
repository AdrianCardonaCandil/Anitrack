package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.anitrack.model.Character
import com.example.anitrack.model.Content
import com.example.anitrack.ui.global.ImagePlaceholder
import com.example.anitrack.ui.global.shimmerEffect
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ContentScreen(
    contentViewModel: ContentViewModel,
    userId:String,
    modifier: Modifier = Modifier
){
    val content: Content? = contentViewModel.content.collectAsState().value
    val characters: List<Character>? = contentViewModel.characters.collectAsState().value

    if (content != null && userId.isNotBlank()) {
        contentViewModel.updateContentListsState(userId, content.id.toString())
    }

    Box {
        HomeScreenUI(
            modifier = modifier,
            content = content,
            characters = characters
        )
        EditContentActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(25.dp),
            onEditContentActionButtonClicked = {contentViewModel.changeDialogVisibility(it)}
        )
        if (content != null) {
            EditContentDialog(
                viewModel = contentViewModel,
                userId = userId,
                contentId = content.id.toString(),
                isActive = contentViewModel.showEditDialog,
                onDismissDialogEvent = { contentViewModel.changeDialogVisibility(it) }
            )
        }
    }

}

@Composable
fun HomeScreenUI(
    content: Content?,
    characters: List<Character>?,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxWidth().heightIn(
                min = 220.dp,
                max = 240.dp
            )
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(content?.backgroundImage)
                    .size(coil.size.Size.ORIGINAL)
                    .build()
            )
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    ImagePlaceholder(modifier = Modifier
                        .fillMaxSize()
                        .background(brush = shimmerEffect())
                    )
                }
                else -> {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        MainInfoContainer(
            contentName = content?.title ?: "",
            contentDescription = content?.synopsis ?: "",
            contentImageUrl = content?.coverImage ?: "",
            contentStatus = content?.status ?: "",
            contentScore = content?.score ?: 0f,
            contentEpisodes = content?.episodes ?: 0,
            contentType = content?.type ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 30.dp,
                    start = 15.dp,
                    end = 15.dp
                )
        )
        Text(
            text = "Information",
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 40.dp,
                    bottom = 10.dp
                ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        AdditionalInfoContainer(
            originalTitle = content?.japaneseTitle ?: "",
            romajiTitle = content?.title ?: "",
            englishTitle = content?.englishTitle ?: "",
            source = content?.source ?: "",
            airedFrom = content?.fromDate ?: "",
            airedTo = content?.toDate ?: "",
            averageDuration = content?.duration ?: "",
            rating = content?.rating ?: "",
            season = content?.season ?: "",
            year = content?.year ?: 0,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        )
        listOf(content?.contentStudios, content?.contentGenres).forEachIndexed { index, value ->
            Spacer(modifier = Modifier.padding(top = 45.dp))
            InfoRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                label = listOf("Studios", "Genres")[index],
                info = value ?: listOf(),
                contentColor = MaterialTheme.colorScheme.onTertiary,
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Text(
            text = "Characters",
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 30.dp,
                    bottom = 10.dp
                ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        CharactersContainer(
            characters = characters ?: listOf(),
            modifier = Modifier.padding(15.dp).heightIn(max = 250.dp)
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
    }
}

@Composable
fun EditContentActionButton(
    modifier: Modifier = Modifier,
    onEditContentActionButtonClicked: (Boolean) -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onEditContentActionButtonClicked(true) },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}
