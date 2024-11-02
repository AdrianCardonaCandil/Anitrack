package com.example.anitrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
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
                gridName = stringResource(it),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    }
}