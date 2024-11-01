package com.example.anitrack.ui.global

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anitrack.navigation.AnitrackRoutes

sealed class BottomNavigationItem(val icon: ImageVector, val label: String, val route: String) {
    data object HomeSearch : BottomNavigationItem(
        icon = Icons.Default.Search,
        label = "Discover",
        route = AnitrackRoutes.HomeSearch.name
    )
    data object Lists : BottomNavigationItem(
        icon = Icons.AutoMirrored.Default.List,
        label = "My Lists",
        route = AnitrackRoutes.Lists.name
    )
    data object Profile : BottomNavigationItem(
        icon = Icons.Default.Person,
        label = "Profile",
        route = AnitrackRoutes.Profile.name
    )

    companion object {
        val values = listOf(HomeSearch, Lists, Profile)
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    NavigationBar(modifier = modifier) {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
        BottomNavigationItem.values.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.label)
                },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                }
            )
        }
    }
}