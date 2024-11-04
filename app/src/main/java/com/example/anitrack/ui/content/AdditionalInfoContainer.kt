package com.example.anitrack.ui.content

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/* This section needs the following information to display:
* originalTitle: String,
* romajiTitle: String,
* englishTitle: String,
* source: String,
* airedDate: String,
* airedTo: String,
* averageDuration: Int,
* rating: String,
* season: String,
* year: Int,
* studios: List<String>
* */

@Composable
fun AdditionalInfoContainer(
    modifier: Modifier = Modifier,
    originalTitle: String = "defaultOriginalTitle",
    romajiTitle: String = "defaultRomajiTitle",
    englishTitle: String = "defaultEnglishTitle",
    source: String = "defaultSource",
    airedFrom: String = "00-00-0000",
    airedTo: String = "00-00-0000",
    averageDuration: Int = 0,
    rating: String = "defaultRating",
    season: String = "defaultSeason",
    year: Int = 0,
    studios: List<String> = listOf("studio1", "studio2")
){
    Column(modifier = modifier) {
        AdditionalInfoLabel(
            label = "Title",
            content = originalTitle
        )
        AdditionalInfoLabel(
            label = "Romaji",
            content = romajiTitle
        )
        AdditionalInfoLabel(
            label = "English",
            content = englishTitle
        )
        AdditionalInfoLabel(
            label = "Source",
            content = source
        )
        AdditionalInfoLabel(
            label = "Aired From",
            content = airedFrom
        )
        AdditionalInfoLabel(
            label = "Aired To",
            content = airedTo
        )
        AdditionalInfoLabel(
            label = "Duration per episode",
            content = "${averageDuration}m"
        )
        AdditionalInfoLabel(
            label = "Rating",
            content = rating
        )
        AdditionalInfoLabel(
            label = "Season",
            content = season
        )
        AdditionalInfoLabel(
            label = "Year",
            content = year.toString()
        )
        StudiosList(
            label = "Studios",
            studios = studios,
            modifier = Modifier.padding(vertical = 8.dp).horizontalScroll(rememberScrollState())
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudiosList(
    label: String,
    studios: List<String>,
    modifier: Modifier = Modifier,
){
    FlowRow (
        modifier = modifier,
        maxLines = 1
    ) {
        Text(
            text = "$label:   ",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.secondary
        )
        studios.forEach { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 5.dp)
            )
        }
    }
}

@Composable
fun AdditionalInfoLabel(
    label: String,
    content: String,
    modifier: Modifier = Modifier
){
    Text(
        text = buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold).toSpanStyle()) {
                append("$label:   ")
            }
            withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                append(content)
            }
        },
        maxLines = 2,
        color = MaterialTheme.colorScheme.secondary,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(vertical = 8.dp).fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdditionalInfoContainerPreview(){
    AdditionalInfoContainer(modifier = Modifier.fillMaxWidth().padding(15.dp))
}
