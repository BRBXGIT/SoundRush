package com.example.onboarding_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.onboarding_screen.screen.OnBoardingScreen
import com.example.onboarding_screen.screen.OnBoardingScreenUtils
import com.example.onboarding_screen.screen.OnBoardingScreenVM
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingScreenRoute(
    @SerialName("code")
    val codee: String? = null // The value name must be different from the serial name
)

fun NavGraphBuilder.onBoardingScreen() = composable<OnBoardingScreenRoute>(
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "http://${OnBoardingScreenUtils.BASIC_DEEP_LINK_DOMAIN}/?code={code}"
        }
    )
) {
    val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

    val accessToken = it.toRoute<OnBoardingScreenRoute>().codee
    OnBoardingScreen(
        viewModel = onBoardingScreenVM,
        accessToken = accessToken
    )
}