package com.example.soundrush

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.CommonVM
import com.example.composition_screen.navigation.compositionScreen
import com.example.navbar_screens.settings_screen.navigation.settingsScreen
import com.example.navbar_screens.user_favourites_screen.navigation.userFavouritesScreen
import com.example.navbar_screens.user_playlists_screen.navigation.userPlaylistsScreen
import com.example.onboarding_screen.navigation.onBoardingScreen
import com.example.playlist_screen.navigation.playlistScreen

@Composable
fun NavGraph(
    startDestination: Any,
) {
    val navController = rememberNavController()

    val commonVM = hiltViewModel<CommonVM>()
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onBoardingScreen()

        userPlaylistsScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        userFavouritesScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        settingsScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        compositionScreen()

        playlistScreen()
    }
}