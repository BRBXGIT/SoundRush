package com.example.navbar_screens.user_playlists_screen.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreen
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object UserPlaylistsScreenRoute

fun NavGraphBuilder.userPlaylistsScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<UserPlaylistsScreenRoute> {
    val userPlaylistsScreenVM = hiltViewModel<UserPlaylistsScreenVM>()
    val userPlaylistsScreenState by userPlaylistsScreenVM.userPlaylistsScreenState.collectAsStateWithLifecycle()

    UserPlaylistsScreen(
        commonVM = commonVM,
        viewModel = userPlaylistsScreenVM,
        screenState = userPlaylistsScreenState,
        commonState = commonState,
        navController = navController
    )
}