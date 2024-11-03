package com.example.anitrack.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anitrack.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentGrid(
    modifier: Modifier = Modifier,
    gridName:String = "Grid Name:",
    defaultItemCount: Int = 4
) {
    Column(modifier = modifier) {
        Text(
            text = gridName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            (1..defaultItemCount).forEach { _ ->
                ContentGridCard(
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchGridPreview(){
    ContentGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
}

val contentGridNameResources: List<Int> = listOf(
    R.string.newSeasonContentGridLabel,
    R.string.topAnimeContentGridLabel,
    R.string.upcomingContentGridLabel
)