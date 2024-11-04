package com.example.anitrack.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
* year: Int
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
    year: Int = 0
){
    Column(modifier = modifier) {
        val infoItems = listOf(
            "Title" to originalTitle,
            "Romaji" to romajiTitle,
            "English" to englishTitle,
            "Source" to source,
            "Aired From" to airedFrom,
            "Aired To" to airedTo,
            "Duration per episode" to "$averageDuration m",
            "Rating" to rating,
            "Season" to season,
            "Year" to year
        )
        infoItems.forEach { (label, value) ->
            AdditionalInfoLabel(
                label = label,
                value = value.toString(),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}


@Composable
fun AdditionalInfoLabel(
    label: String,
    value: String,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier) {
        Text(
            text = "$label:",
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            modifier = Modifier
                .weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdditionalInfoContainerPreview(){
    AdditionalInfoContainer(modifier = Modifier.fillMaxWidth().padding(15.dp))
}
