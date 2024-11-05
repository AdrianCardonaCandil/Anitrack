package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
                    top = 30.dp,
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
                    top = 40.dp,
                    bottom = 10.dp
                ),
            color = MaterialTheme.colorScheme.primary,
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
        listOf(1, 2).forEach {
            Spacer(modifier = Modifier.padding(top = 45.dp))
            InfoRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                contentColor = MaterialTheme.colorScheme.onTertiary,
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Text(
            text = "Characters",
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 30.dp,
                    bottom = 10.dp
                ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        CharactersContainer(
            characters = listOf(
                "character1",
                "character2",
                "character3",
                "character4",
                "character5",
                "character6",
                "character7",
                "character8",
                "character9",
                "character10"
            ),
            modifier = Modifier.padding(15.dp).heightIn(max = 250.dp)
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
    }
}