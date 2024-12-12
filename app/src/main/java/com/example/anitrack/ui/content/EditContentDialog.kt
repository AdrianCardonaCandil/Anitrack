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
import com.example.anitrack.ui.lists.ListHandler

@Composable
fun EditContentDialog(
    modifier: Modifier = Modifier,
    viewModel: ContentViewModel,
    userId: String,
    contentId: String,
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
                    ListHandler.ListType.entries.forEach { listType ->
                        val isInList = viewModel.contentListsState.value.contains(listType)
                        if(listType != ListHandler.ListType.FAVORITES) {
                            OutlinedListButton(
                                name = stringResource(id = getListNameResource(listType)),
                                isSelected = isInList,
                                onClick = {
                                    if (isInList) {
                                        viewModel.removeFromList(userId, contentId, listType)
                                    } else {
                                        viewModel.addToList(userId, contentId, listType)
                                    }
                                },
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 25.dp))
                    Text(
                        text = stringResource(R.string.AddToLovedLabel),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (viewModel.contentListsState.value.contains(ListHandler.ListType.FAVORITES))
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp)
                            .size(75.dp)
                            .clickable {
                                if (viewModel.contentListsState.value.contains(ListHandler.ListType.FAVORITES)) {
                                    viewModel.removeFromList(userId, contentId, ListHandler.ListType.FAVORITES)
                                } else {
                                    viewModel.addToList(userId, contentId, ListHandler.ListType.FAVORITES)
                                }
                            },
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun OutlinedListButton(name: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(vertical = 5.dp),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}
@Composable
fun getListNameResource(listType: ListHandler.ListType): Int {
    return when (listType) {
        ListHandler.ListType.WATCHING -> R.string.watchingListName
        ListHandler.ListType.COMPLETED -> R.string.CompletedListName
        ListHandler.ListType.PLAN_TO_WATCH -> R.string.PlanToWatchListName
        ListHandler.ListType.FAVORITES -> R.string.AddToLovedLabel
    }
}