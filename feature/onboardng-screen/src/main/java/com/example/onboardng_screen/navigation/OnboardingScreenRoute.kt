package com.example.onboardng_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingScreenRoute

fun NavGraphBuilder.onboardingScreen() = composable<OnboardingScreenRoute> {

}