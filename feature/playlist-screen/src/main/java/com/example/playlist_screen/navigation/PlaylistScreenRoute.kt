package com.example.playlist_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.playlist_screen.screen.PlaylistScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistScreenRoute(
    val playlistId: Int
)

fun NavGraphBuilder.playlistScreen() = composable<PlaylistScreenRoute> {
    val playlistId = it.toRoute<PlaylistScreenRoute>().playlistId

    PlaylistScreen()
}