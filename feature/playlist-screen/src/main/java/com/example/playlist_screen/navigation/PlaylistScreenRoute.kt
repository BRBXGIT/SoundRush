package com.example.playlist_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.playlist_screen.screen.PlaylistScreen
import com.example.playlist_screen.screen.PlaylistScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistScreenRoute(
    val playlistId: Int
)

fun NavGraphBuilder.playlistScreen(
    navController: NavController,
    commonVM: CommonVM,
    commonState: CommonState
) = composable<PlaylistScreenRoute> {
    val playlistId = it.toRoute<PlaylistScreenRoute>().playlistId
    val playlistScreenVM = hiltViewModel<PlaylistScreenVM>()
    val playlistScreenState = playlistScreenVM.playlistScreenState.collectAsStateWithLifecycle().value

    PlaylistScreen(
        navController = navController,
        playlistId = playlistId,
        viewModel = playlistScreenVM,
        screenState = playlistScreenState,
        commonState = commonState,
        commonVM = commonVM
    )
}