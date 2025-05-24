package com.example.playlist_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.playlist_screen.screen.PlaylistScreen
import kotlinx.serialization.Serializable

@Serializable
data object PlaylistScreenRoute

fun NavGraphBuilder.playlistScreen() = composable<PlaylistScreenRoute> {
    PlaylistScreen()
}