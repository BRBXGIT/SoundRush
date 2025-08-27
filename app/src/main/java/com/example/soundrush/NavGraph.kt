package com.example.soundrush

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.bars.NavBar
import com.example.common.state.CommonIntent
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            startDestination = startDestination,
            navController = navController,
            modifier = Modifier.align(Alignment.Center)
        ) {
            onboardingScreen()

            homeScreen(
                navController = navController,
                commonVM = commonVM,
                homeScreenVM = homeScreenVM
            )
        }

        val commonState by commonVM.commonState.collectAsStateWithLifecycle()
        NavBar(
            selectedItemIndex = commonState.currentNavIndex,
            onNavItemClick = { index, destination ->
                commonVM.sendIntent(CommonIntent.SetNavIndex(index))
            }
        )
    }
}