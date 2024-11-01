package com.example.anitrack.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anitrack.navigation.AnitrackRoutes
import com.example.anitrack.ui.content.ContentScreen
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
        startDestination = AnitrackRoutes.HomeSearch.name,
        navController = navController,
        modifier = modifier
    ) {
        composable(route = AnitrackRoutes.HomeSearch.name){
            HomeSearchScreen()
        }
        composable(route = AnitrackRoutes.Content.name){
            ContentScreen()
        }
        composable(route = AnitrackRoutes.Lists.name){
            ListsScreen()
        }
        composable(route = AnitrackRoutes.Profile.name){
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