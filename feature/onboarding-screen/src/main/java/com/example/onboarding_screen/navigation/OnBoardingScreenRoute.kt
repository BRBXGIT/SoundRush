package com.example.onboarding_screen.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val codee: String? = null, // The value name must be different from the serial name
    @SerialName("state")
    val statee: String? = null
)

fun NavGraphBuilder.onBoardingScreen() = composable<OnBoardingScreenRoute>(
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "https://${OnBoardingScreenUtils.BASIC_DEEP_LINK_DOMAIN}/?code={code}&state={state}"
        }
    )
) {
    val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()
    // Initialize here to avoid recompositions and drop the state after signing in
    val onBoardingScreenState by onBoardingScreenVM.onBoardingScreenState.collectAsStateWithLifecycle()

    val code = it.toRoute<OnBoardingScreenRoute>().codee
    val state = it.toRoute<OnBoardingScreenRoute>().statee
    OnBoardingScreen(
        viewModel = onBoardingScreenVM,
        code = code,
        state = state,
        screenState = onBoardingScreenState
    )
}