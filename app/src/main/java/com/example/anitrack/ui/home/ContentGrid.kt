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
import androidx.compose.ui.unit.dp
import com.example.anitrack.model.Content

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentGrid(
    modifier: Modifier = Modifier,
    onCardClicked: (id: Int) -> Unit,
    gridName:String = "Grid Name:",
    contentList: List<Content>?
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
            contentList?.forEach { content ->
                ContentGridCard(
                    contentTitle = content.title,
                    contentImageUrl = content.coverImage,
                    contentId = content.id,
                    onClick = { onCardClicked(it) },
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}