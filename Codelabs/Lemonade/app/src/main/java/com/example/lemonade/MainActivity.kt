package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonadeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LemonadeImagesAndButton(modifier: Modifier = Modifier){
    var imageSelector by remember { mutableIntStateOf(1) }
    var squeezedTimes by remember { mutableIntStateOf(0) }
    var requiredSqueezes by remember { mutableIntStateOf((2..4).random()) }
    Column(modifier, horizontalAlignment = Alignment
        .CenterHorizontally) {
        Button(onClick = {
            when(imageSelector){
                1, 3 -> imageSelector++
                2 -> {
                    if (squeezedTimes < requiredSqueezes) {
                        squeezedTimes ++
                    } else {
                        squeezedTimes = 0
                        requiredSqueezes = (2..4).random()
                        imageSelector ++
                    }
                }
                else -> imageSelector = 1
            }
        }) {
            Image(
                painter = painterResource(selectImage(imageSelector)),
                contentDescription = stringResource(selectDescription(imageSelector))
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = stringResource(selectLabel(imageSelector)),
            fontSize = 18.sp
        )
    }

}

fun selectLabel(imageSelector: Int): Int {
    return when(imageSelector){
        1 -> R.string.lemon_tree_label
        2 -> R.string.lemon_label
        3 -> R.string.lemonade_label
        else -> R.string.empty_glass_label
    }
}

fun selectImage(imageSelector: Int): Int {
    return when(imageSelector){
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
}

fun selectDescription(imageSelector: Int): Int {
    return when(imageSelector){
        1 -> R.string.lemon_tree_description
        2 -> R.string.lemon_description
        3 -> R.string.lemonade_description
        else -> R.string.empty_glass_description
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    LemonadeImagesAndButton(modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}