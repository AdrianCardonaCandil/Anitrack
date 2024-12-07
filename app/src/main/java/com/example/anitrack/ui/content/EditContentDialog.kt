package com.example.anitrack.ui.content
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.anitrack.R

@Composable
fun EditContentDialog(
    modifier: Modifier = Modifier,
    onDismissDialogEvent: (Boolean) -> Unit,
    isActive: Boolean
){
    if (isActive) {
        Dialog(
            onDismissRequest = { onDismissDialogEvent(false) }
        ) {
            Box(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(50.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.AddToListLabel),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.padding(vertical = 7.5.dp))
                    listOf(R.string.PlanToWatchListName, R.string.watchingListName, R.string.CompletedListName).forEach {
                        OutlinedListButton(
                            name = stringResource(it),
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 25.dp))
                    Text(
                        text = stringResource(R.string.AddToLovedLabel),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                            .size(75.dp)
                            .clickable { TODO() },
                        contentDescription = stringResource(R.string.favouriteCD)
                    )
                }
            }
        }
    }
}

@Composable
fun OutlinedListButton(name: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier,
        onClick = { TODO() }
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}