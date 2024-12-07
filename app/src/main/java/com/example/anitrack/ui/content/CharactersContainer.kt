package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.anitrack.R
import com.example.anitrack.model.Character
import com.example.anitrack.ui.global.ImagePlaceholder
import com.example.anitrack.ui.global.shimmerEffect

@Composable
fun CharactersContainer(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    imageSize: Int = 100,
    spacing: Int = 25
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(imageSize.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.dp),
        modifier = modifier
    ) {
        items(characters) { character ->
            CharacterCard(character = character.character, imageSize = imageSize)
        }
    }
}

@Composable
fun CharacterCard(
    character: Character.CharacterData?,
    imageSize: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterImage(
            characterImageUrl = character?.image ?: "",
            imageSize = imageSize
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = character?.name ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CharacterImage(
    characterImageUrl: String?,
    imageSize: Int
) {
    Box(
        modifier = Modifier.size(imageSize.dp).clip(CircleShape)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(characterImageUrl)
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
                    contentDescription = stringResource(R.string.characterImageCD),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}