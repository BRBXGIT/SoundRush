package com.example.soundrush

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.onboardng_screen.navigation.OnboardingScreenRoute
import com.example.onboardng_screen.navigation.onboardingScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        startDestination = OnboardingScreenRoute(),
        navController = navController
    ) {
        onboardingScreen(navController)
    }

    // TODO - create bottom bar without scaffold
}