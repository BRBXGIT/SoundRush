package com.example.soundrush

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onBoardingScreen()

        userPlaylistsScreen()

        userFavouritesScreen()

        settingsScreen()

        compositionScreen()

        playlistScreen()
    }
}