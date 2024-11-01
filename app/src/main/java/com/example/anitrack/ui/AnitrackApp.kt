package com.example.anitrack.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anitrack.AnitrackAppScreens
import com.example.anitrack.ui.homesearch.HomeSearchScreen
import com.example.anitrack.ui.lists.ListsScreen
import com.example.anitrack.ui.profile.ProfileScreen
import com.example.anitrack.ui.theme.AnitrackTheme

@Composable
fun AnitrackApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        startDestination = AnitrackAppScreens.HomeSearch.name,
        navController = navController,
        modifier = modifier
    ) {
        composable(route = AnitrackAppScreens.HomeSearch.name){
            HomeSearchScreen()
        }
        composable(route = AnitrackAppScreens.Lists.name){
            ListsScreen()
        }
        composable(route = AnitrackAppScreens.Profile.name){
            ProfileScreen()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AnitrackAppPreview() {
    AnitrackTheme {
        AnitrackApp()
    }
}