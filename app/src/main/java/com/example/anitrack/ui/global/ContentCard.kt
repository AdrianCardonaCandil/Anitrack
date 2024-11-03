package com.example.anitrack.ui.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anitrack.R

/* This card needs the following attributes to draw the information needed on the screen
contentTitle:String,
contentEpisodes: Int,
contentType: String,
contentImageUrl: String,
contentGenres: List<String>,
contentScore: Int,
showEpisodes: Boolean
* */

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    contentTitle: String = "DefaultContentTitle",
    contentEpisodes: Int = 0,
    contentType: String = "DefaultContentType",
    contentImageUrl: String = "DefaultContentImageUrl",
    contentGenres: List<String> = listOf("Genre1", "Genre2", "Genre3"),
    contentScore: Int = 0,
    showEpisodes: Boolean = false
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.coverimage),
                contentDescription = null,
                modifier = Modifier.clip(MaterialTheme.shapes.extraSmall)
            )
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp
                    ),
            ) {
                Text(
                    text = contentTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
                if (showEpisodes){
                    EpisodesHandler(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.padding(vertical = 50.dp))
                }
                GenresList(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .horizontalScroll(rememberScrollState())
                )
                Spacer(modifier = Modifier.padding(top = 5.dp))
                Text(
                    text = contentType,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer).padding(5.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable // DONE
fun EpisodesHandler(modifier: Modifier = Modifier){
    EpisodesIndicator(
        modifier = modifier
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.padding(start = 5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun EpisodesIndicator(
    modifier: Modifier = Modifier,
    label: String = "Episodes:",
    currentNumber: Int = 0,
    contentEpisodes: Int = 0,
    progressFactor: Float = 0.5f
){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "${currentNumber}/${contentEpisodes}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        LinearProgressIndicator(
            progress = { progressFactor },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .height(20.dp)
                .clip(MaterialTheme.shapes.extraSmall)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable // DONE
fun GenresList(
    modifier: Modifier = Modifier,
    contentGenres: List<String> = listOf("Genre1", "Genre2", "Genre3"),
) {
    FlowRow(
        modifier = modifier,
        maxLines = 1,
    ) {
        contentGenres.forEach { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true

)
@Composable
fun ContentCardPreview(){
    ContentCard(modifier = Modifier
        .fillMaxWidth().padding(15.dp)
    )
}