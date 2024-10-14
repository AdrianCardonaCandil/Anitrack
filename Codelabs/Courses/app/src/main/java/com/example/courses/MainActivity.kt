package com.example.courses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.courses.data.DataSource
import com.example.courses.model.Topic
import com.example.courses.ui.theme.CoursesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoursesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopicGridApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier){
    Card(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(topic.imageResourceId),
                contentDescription = stringResource(topic.nameResourceId),
                modifier = Modifier.size(68.dp)
            )
            Column {
                Text(
                    text = stringResource(topic.nameResourceId),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = "icon-for-topic",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = topic.totalCourses.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun TopicGrid(topicList: List<Topic>, modifier: Modifier = Modifier){
    LazyVerticalGrid (
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(topicList) { topic ->
            TopicCard(topic)
        }
    }
}

@Composable
fun TopicGridApp(modifier: Modifier = Modifier){
    TopicGrid(DataSource.topics, modifier = modifier.padding(8.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopicCardPreview(){
    TopicGridApp()
}

