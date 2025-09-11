package com.example.playlist_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.common.state.CommonVM
import com.example.playlist_screen.screen.PlaylistScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistScreenRoute(
    val playlistUrn: String,
    val playlistName: String
)

fun NavGraphBuilder.playlistScreen(
    navController: NavController,
    commonVM: CommonVM
) = composable<PlaylistScreenRoute> {
    val playlistUrn = it.toRoute<PlaylistScreenRoute>().playlistUrn
    val playlistName = it.toRoute<PlaylistScreenRoute>().playlistName

    PlaylistScreen(
        navController = navController,
        playlistUrn = playlistUrn,
        commonVM = commonVM,
        playlistName = playlistName
    )
}