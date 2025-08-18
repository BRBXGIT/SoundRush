package com.example.onboardng_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.onboardng_screen.screen.OnboardingScreen
import com.example.onboardng_screen.screen.OnboardingScreenVM
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingScreenRoute(
    @SerialName("code")
    val code: String? = null,
)

fun NavGraphBuilder.onboardingScreen() = composable<OnboardingScreenRoute>(
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "https://soundrush-6c78e.web.app/?code={code}"
        }
    )
) {
    val onboardingScreenVM = hiltViewModel<OnboardingScreenVM>()
    val code = it.toRoute<OnboardingScreenRoute>().code

    OnboardingScreen(
        viewModel = onboardingScreenVM,
        code = code,
    )
}