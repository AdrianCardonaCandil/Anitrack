package com.example.anitrack.ui.global

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.anitrack.R
import com.example.anitrack.navigation.AnitrackRoutes

sealed class BottomNavigationItem(val icon: ImageVector, val label: Int, val route: String) {
    data object Home : BottomNavigationItem(
        icon = Icons.Default.Home,
        label = R.string.bottomNavHomeLabel,
        route = AnitrackRoutes.Home.name
    )
    data object Search : BottomNavigationItem(
        icon = Icons.Default.Search,
        label = R.string.bottomNavSearchLabel,
        route = AnitrackRoutes.Search.name
    )
    data object Lists : BottomNavigationItem(
        icon = Icons.AutoMirrored.Default.List,
        label = R.string.bottomNavListsLabel,
        route = AnitrackRoutes.Lists.name
    )
    data object Profile : BottomNavigationItem(
        icon = Icons.Default.Person,
        label = R.string.bottomNavProfileLabel,
        route = AnitrackRoutes.Profile.name
    )

    companion object {
        val values = listOf(Home, Search, Lists, Profile)
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
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
                    Text(text = stringResource(item.label))
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