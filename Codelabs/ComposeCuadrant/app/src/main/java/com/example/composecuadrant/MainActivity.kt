package com.example.composecuadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecuadrant.ui.theme.ComposeCuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCuadrantTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuadrantLayout(Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuadrantLayout(modifier: Modifier = Modifier){
    Column(modifier) {
        Row(modifier = Modifier.weight(1f)) {
            QuadrantCard(
                title = stringResource(R.string.first_quadrant_name),
                description = stringResource(R.string.first_quadrant_desc),
                modifier = Modifier.weight(1f),
                color = colorResource(R.color.quadrant1_color)
            )
            QuadrantCard(
                title = stringResource(R.string.second_quadrant_name),
                description = stringResource(R.string.second_quadrant_desc),
                modifier = Modifier.weight(1f),
                color = colorResource(R.color.quadrant2_color)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            QuadrantCard(
                title = stringResource(R.string.third_quadrant_name),
                description = stringResource(R.string.third_quadrant_desc),
                modifier = Modifier.weight(1f),
                color = colorResource(R.color.quadrant3_color)
            )
            QuadrantCard(
                title = stringResource(R.string.fourth_quadrant_name),
                description = stringResource(R.string.fourth_quadrant_desc),
                modifier = Modifier.weight(1f),
                color = colorResource(R.color.quadrant4_color)
            )
        }
    }
}

@Composable
fun QuadrantCard(title:String, description:String, color: Color, modifier: Modifier = Modifier){
    Column (
        modifier.fillMaxHeight().background(color = color).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 16.dp
            )
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ComposeCuadrantTheme {
        QuadrantLayout(Modifier.fillMaxSize())
    }
}