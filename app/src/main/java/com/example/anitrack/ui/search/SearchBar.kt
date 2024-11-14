package com.example.anitrack.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anitrack.R

@Composable
fun SearchBar(
    onUserInput: (String) -> Unit,
    currentInput: String,
    modifier: Modifier = Modifier
){
    Column(modifier) {
        Text(
            text = stringResource(R.string.searchBarLabel),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold

        )
        TextField(
            value = currentInput,
            onValueChange = {onUserInput(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            singleLine = true
        )
    }
}