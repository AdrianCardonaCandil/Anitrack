package com.example.anitrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onGridContentClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier) {
        Banner(modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = 475.dp,
                max = 500.dp
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(15.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        contentGridNameResources.forEach {
            ContentGrid(
                onCardClicked = { onGridContentClicked() },
                gridName = stringResource(it),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
        Text(
            text = homeViewModel.totalContentsReceived,
            modifier = Modifier.padding(30.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}