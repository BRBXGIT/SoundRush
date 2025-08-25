package com.example.soundrush

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.state.CommonVM
import com.example.home_screen.navigation.homeScreen
import com.example.home_screen.screen.HomeScreenVM
import com.example.onboardng_screen.navigation.onboardingScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    // Initialize here to don't reinitialize
    val commonVM = hiltViewModel<CommonVM>()

    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        onboardingScreen()

        homeScreen(
            navController = navController,
            commonVM = commonVM,
            homeScreenVM = homeScreenVM
        )
    }

    // TODO - create bottom bar without scaffold
}