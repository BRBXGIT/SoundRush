package com.example.onboardng_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    @SerialName("state")
    val statee: String? = null,
    @SerialName("error")
    val errorr: String? = null
)

fun NavGraphBuilder.onboardingScreen() = composable<OnboardingScreenRoute>(
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "https://soundrush-6c78e.web.app/?error={error}&state={state}"
        }
    )
) {
    val onboardingScreenVM = hiltViewModel<OnboardingScreenVM>()
    val state = it.toRoute<OnboardingScreenRoute>().statee
    val error = it.toRoute<OnboardingScreenRoute>().errorr

    OnboardingScreen(
        viewModel = onboardingScreenVM,
        state = state,
        error = error
    )
}