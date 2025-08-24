package com.example.home_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.state.CommonVM
import com.example.home_screen.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    commonVM: CommonVM
) = composable<HomeScreenRoute> {
    HomeScreen(
        navController = navController,
        commonVM = commonVM
    )
}