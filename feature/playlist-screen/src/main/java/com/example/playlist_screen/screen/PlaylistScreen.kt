package com.example.playlist_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.common.CommonIntent
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.playlist_screen.sections.PlaylistHeader
import com.example.playlist_screen.sections.PlaylistScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    navController: NavController,
    playlistId: Int,
    viewModel: PlaylistScreenVM,
    screenState: PlaylistScreenState,
    commonState: CommonState,
    commonVM: CommonVM
) {
    // Set playlist id to state and fetch playlist
    LaunchedEffect(playlistId, screenState.accessToken) {
        if (screenState.accessToken != null) {
            viewModel.sendIntent(
                PlaylistScreenIntent.UpdateScreenState(screenState.copy(playlistId = playlistId))
            )
            viewModel.sendIntent(
                PlaylistScreenIntent.FetchPlaylist(
                    onUnauthorized = {
                        if (!commonState.isUserTokensRefreshing) {
                            commonVM.sendIntent(
                                CommonIntent.RefreshUserTokens(
                                    refreshToken = screenState.refreshToken!!,
                                    onComplete = {
                                        viewModel.sendIntent(PlaylistScreenIntent.FetchTokens)
                                        viewModel.sendIntent(
                                            PlaylistScreenIntent.FetchPlaylist(onUnauthorized = {})
                                        )
                                    }
                                )
                            )
                        }
                    }
                )
            )
        }
    }

    val playlist = screenState.playlist
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            PlaylistScreenTopBar(
                playlistName = if (playlist?.title == null) "Loading..." else playlist.title,
                scrollBehavior = scrollBehavior,
                onNavIconClick = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onRefresh = {},
                isRefreshing = screenState.isLoading
            ) {
                playlist?.let {
                    PlaylistHeader(
                        posterPath = playlist.artworkUrl,
                        playlistName = playlist.title,
                        tracksAmount = playlist.trackCount,
                        playlistDuration = playlist.duration,
                        createdBy = playlist.user.fullName,
                        description = playlist.description
                    )

                }
            }
        }
    }
}