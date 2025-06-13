package com.example.soundrush

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.CommonVM
import com.example.composition_screen.navigation.compositionScreen
import com.example.navbar_screens.settings_screen.navigation.settingsScreen
import com.example.navbar_screens.user_likes_screen.navigation.userLikesScreen
import com.example.navbar_screens.user_playlists_screen.navigation.userPlaylistsScreen
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenVM
import com.example.onboarding_screen.navigation.onBoardingScreen
import com.example.playlist_screen.navigation.playlistScreen

@Composable
fun NavGraph(
    startDestination: Any,
) {
    val navController = rememberNavController()

    // Initialize here to don't recreate values
    val commonVM = hiltViewModel<CommonVM>()

    val commonState = commonVM.commonState.collectAsStateWithLifecycle().value
    val userPlaylistsScreenVM = hiltViewModel<UserPlaylistsScreenVM>()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onBoardingScreen()

        userPlaylistsScreen(
            userPlaylistsScreenVM = userPlaylistsScreenVM,
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        userLikesScreen(
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

        playlistScreen(
            commonVM = commonVM,
            commonState = commonState
        )
    }
}