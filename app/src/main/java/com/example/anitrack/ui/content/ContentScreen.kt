package com.example.anitrack.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.anitrack.R

@Composable
fun ContentScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.shoujoshuumatsuryokou),
            contentDescription = null
        )
        MainInfoContainer()
    }
}