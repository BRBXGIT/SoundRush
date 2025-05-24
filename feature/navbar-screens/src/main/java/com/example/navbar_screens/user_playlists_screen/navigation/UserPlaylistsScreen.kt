package com.example.navbar_screens.user_playlists_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreen
import kotlinx.serialization.Serializable

@Serializable
data object UserPlaylistsScreen

fun NavGraphBuilder.userPlaylistsScreen() = composable<UserPlaylistsScreen> {
    UserPlaylistsScreen()
}