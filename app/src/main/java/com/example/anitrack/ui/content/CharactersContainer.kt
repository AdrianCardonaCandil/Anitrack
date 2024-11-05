package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anitrack.R

@Composable
fun CharactersContainer(
    modifier: Modifier = Modifier,
    characters: List<String>,
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
            CharacterCard(character = character, imageSize = imageSize)
        }
    }
}

@Composable
fun CharacterCard(
    character: String,
    imageSize: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterImage(imageSize = imageSize)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = character,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CharacterImage(imageSize: Int) {
    Image(
        painter = painterResource(R.drawable.character),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(imageSize.dp)
            .clip(CircleShape)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharactersContainerPreview(){
    CharactersContainer(
        characters = listOf(
            "character1",
            "character2",
            "character3",
            "character4",
            "character5",
            "character6"
        ),
        modifier = Modifier.padding(15.dp)
    )
}