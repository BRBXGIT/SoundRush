package com.example.home_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home_screen.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

fun NavGraphBuilder.homeScreen(
    navController: NavController
) = composable<HomeScreenRoute> {
    HomeScreen(
        navController = navController
    )
}