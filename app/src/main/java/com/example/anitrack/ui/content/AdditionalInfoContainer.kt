package com.example.anitrack.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
    originalTitle: String = "",
    romajiTitle: String = "",
    englishTitle: String = "",
    source: String = "",
    airedFrom: String = "",
    airedTo: String = "",
    averageDuration: String = "",
    rating: String = "",
    season: String = "",
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
            "Duration" to averageDuration,
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
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,

        )
        Text(
            text = value,
            modifier = Modifier
                .weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    HorizontalDivider(
        thickness = 0.25.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}
