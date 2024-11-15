package com.example.anitrack.ui.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.anitrack.model.Content

/* This card needs the following attributes to draw the information needed on the screen
* contentTitle:String,
* contentEpisodes: Int,
* contentType: String,
* contentImageUrl: String,
* contentGenres: List<String>,
* showEpisodes: Boolean
* */

/* IMPORTANTE: Cuando uses el contentCard tienes que pasarle el siguiente modificador
*  modifier = Modifier
*         .fillMaxWidth()
*         .padding(
*             top = 15.dp,
*             start = 15.dp,
*             end = 15.dp
*         )
*  Los padding horizontales (start y end) realmente dependen de cuanto padding estés
*  dejando para los componentes de tus páginas en los bordes izquierdo y derecho. Los
*  mios tienen 15.dp en cada borde.
* */

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    content: Content,
    userContentEpisodes: Int = 0,
    showEpisodes: Boolean = true,
    showProgressControls: Boolean = false,
    onEpisodeIncrement: (Int) -> Unit,
    onEpisodeDecrement: (Int) -> Unit,
    onCardClicked: (Int) -> Unit
) {
    var currentEpisodes by remember { mutableStateOf(userContentEpisodes) }
    LaunchedEffect(userContentEpisodes) {
        currentEpisodes = userContentEpisodes
    }

    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked(content.id) },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .widthIn(
                        min = 135.dp,
                        max = 145.dp
                    ).clip(MaterialTheme.shapes.extraSmall)
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(content.coverImage)
                            .size(coil.size.Size.ORIGINAL)
                            .build()
                    )
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            ImagePlaceholder(modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3 / 4.25f)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(brush = shimmerEffect())
                            )
                        }
                        else -> {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .aspectRatio(3 / 4.25f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(
                            top = 10.dp, bottom = 10.dp,
                            start = 20.dp, end = 5.dp
                        ),
                ) {
                    Text(
                        text = content.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall
                    )
                    if (showEpisodes && showProgressControls) {
                        EpisodesHandler(
                            totalContentEpisodes = content.episodes ?: 0,
                            userContentEpisodes = currentEpisodes,
                            onEpisodeIncrement = {
                                if (currentEpisodes < (content.episodes ?: 0)) {
                                    currentEpisodes++
                                    onEpisodeIncrement(content.id)
                                }
                            },
                            onEpisodeDecrement = {
                                if (currentEpisodes > 0) {
                                    currentEpisodes--
                                    onEpisodeDecrement(content.id)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.padding(vertical = 40.dp))
                    }
                    GenresList(
                        contentGenres = content.contentGenres ?: listOf(),
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .horizontalScroll(rememberScrollState())
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    Text(
                        text = content.type ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(5.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1
                    )
                }
            }
        }
        HorizontalDivider(
            thickness = 0.25.dp,
            modifier = Modifier.padding(top = 15.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun EpisodesHandler(
    totalContentEpisodes: Int,
    userContentEpisodes: Int,
    onEpisodeIncrement: () -> Unit,
    onEpisodeDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    EpisodesIndicator(
        totalContentEpisodes = totalContentEpisodes,
        userContentEpisodes = userContentEpisodes,
        modifier = modifier
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondary)
                .clickable {
                    // Solo incrementar si no ha alcanzado el límite
                    if (userContentEpisodes < totalContentEpisodes) {
                        onEpisodeIncrement()
                    }
                }
        )
        Spacer(modifier = Modifier.padding(start = 5.dp))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondary)
                .clickable {
                    // Solo decrementar si no es menor a 0
                    if (userContentEpisodes > 0) {
                        onEpisodeDecrement()
                    }
                }
        )
    }
}



@Composable
fun EpisodesIndicator(
    modifier: Modifier = Modifier,
    label: String = "Episodes:",
    totalContentEpisodes: Int,
    userContentEpisodes: Int,
) {
    val progressFactor = if (totalContentEpisodes > 0) {
        userContentEpisodes / totalContentEpisodes.toFloat()
    } else {
        0f
    }

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
                text = "$userContentEpisodes/$totalContentEpisodes",
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
                .clip(MaterialTheme.shapes.extraSmall),
        )
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable // DONE
fun GenresList(
    modifier: Modifier = Modifier,
    contentGenres: List<String?>,
) {
    FlowRow(
        modifier = modifier,
        maxLines = 1,
    ) {
        contentGenres.forEach { item ->
            Text(
                text = item ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}