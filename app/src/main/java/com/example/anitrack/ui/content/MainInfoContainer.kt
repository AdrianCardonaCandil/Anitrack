package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.anitrack.R
import com.example.anitrack.ui.global.ImagePlaceholder
import com.example.anitrack.ui.global.shimmerEffect

/* This section needs the following information to display:
* contentName: String,
* contentDescription: String,
* contentType: String,
* contentEpisodes: Int,
* contentStatus: String,
* contentScore: Float
* contentCoverImageUrl: String
* */

@Composable
fun MainInfoContainer(
    modifier: Modifier = Modifier,
    contentName: String = "",
    contentDescription: String = "",
    contentStatus: String = "",
    contentType: String = "",
    contentEpisodes: Int = 0,
    contentScore: Float = 0f,
    contentImageUrl: String = ""
){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.widthIn(
                    min = 135.dp,
                    max = 145.dp
                ).clip(MaterialTheme.shapes.extraSmall)
                    .align(Alignment.CenterVertically)
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(contentImageUrl)
                        .size(coil.size.Size.ORIGINAL)
                        .build()
                )
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        ImagePlaceholder(modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3/4.25f)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(brush = shimmerEffect())
                        )
                    }
                    else -> {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(R.string.contentImageCD),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.extraSmall)
                                .aspectRatio(3/4.25f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = contentName,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                InfoTagsLayout(
                    contentType = contentType,
                    contentEpisodes = contentEpisodes,
                    contentStatus = contentStatus,
                    contentScore = contentScore,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            text = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 25.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoTagsLayout(
    contentType: String,
    contentEpisodes: Int,
    contentStatus: String,
    contentScore: Float,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.padding(top = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        InfoTag(contentType)
        InfoTag(contentStatus)
        InfoTag("$contentEpisodes episodes")
        InfoTag("Score: $contentScore")
    }
}

@Composable
fun InfoTag(content: String){
    Text(
        text = content,
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(5.dp).horizontalScroll(rememberScrollState()),
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.labelSmall
    )
}