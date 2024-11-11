package com.example.anitrack.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.anitrack.ui.global.ImagePlaceholder
import com.example.anitrack.ui.global.shimmerEffect

/* This card will need as follows to draw the content on the screen
contentName:String,
contentImageUrl:String,
contentId: Int
* */

@Composable
fun ContentGridCard(
    modifier: Modifier = Modifier,
    contentTitle: String?,
    contentImageUrl: String?,
    contentId: Int = 0,
    onClick: (id: Int) -> Unit,
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier.clickable { onClick(contentId) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.widthIn(
                min = 135.dp,
                max = 145.dp
            )
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
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.extraSmall)
                            .aspectRatio(3/4.25f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = contentTitle ?: "No Title",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp).widthIn(max = 150.dp),
            )
        }
    }
}