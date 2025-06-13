package com.example.playlist_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.common.CommonIntent
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.design_system.theme.mColors

@Composable
fun PlaylistScreen(
    playlistId: Int,
    viewModel: PlaylistScreenVM,
    screenState: PlaylistScreenState,
    commonState: CommonState,
    commonVM: CommonVM
) {
    // Set playlist id to state and fetch playlist
    LaunchedEffect(playlistId) {
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = screenState.playlist?.id.toString()
            )
        }
    }
}