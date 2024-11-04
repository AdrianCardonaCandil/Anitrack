package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anitrack.R

@Composable
fun ContentScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.shoujoshuumatsuryokou),
            contentDescription = null
        )
        MainInfoContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 15.dp,
                    end = 15.dp
                )
        )
        Text(
            text = "Information",
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 30.dp,
                    bottom = 10.dp
                ),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        AdditionalInfoContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp
                )
        )
    }
}