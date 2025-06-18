package com.example.playlist_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.CommonIntent
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.design_system.theme.mColors
import com.example.design_system.track_item.TrackItem
import com.example.playlist_screen.sections.PlaylistHeader
import com.example.playlist_screen.sections.PlaylistScreenTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                playlistName = playlist?.title,
                scrollBehavior = scrollBehavior,
                onNavIconClick = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            // Simple feature to deceive user :)
            val refreshScope = rememberCoroutineScope()

            PullToRefreshBox(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onRefresh = {
                    refreshScope.launch {
                        viewModel.sendIntent(
                            PlaylistScreenIntent.UpdateScreenState(
                                screenState.copy(isLoading = true)
                            )
                        )
                        delay(2000)
                        viewModel.sendIntent(
                            PlaylistScreenIntent.UpdateScreenState(
                                screenState.copy(isLoading = false)
                            )
                        )
                    }
                },
                isRefreshing = screenState.isLoading
            ) {
                playlist?.let {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(PlaylistScreenDimens.TRACKS_LC_VERTICAL_ARRANGEMENT.dp),
                        contentPadding = PaddingValues(vertical = PlaylistScreenDimens.TRACKS_LC_CONTENT_PADDING.dp)
                    ) {
                        item {
                            PlaylistHeader(
                                posterPath = playlist.artworkUrl,
                                playlistName = playlist.title,
                                tracksAmount = playlist.trackCount,
                                playlistDuration = playlist.duration,
                                createdBy = playlist.user.username,
                            )
                        }

                        items(playlist.tracks) { track ->
                            TrackItem(
                                posterPath = track.artworkUrl,
                                trackName = track.title,
                                author = track.user.username,
                                trackDuration = track.duration,
                                streamCount = track.playbackCount,
                                onClick = {},
                                onLongClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}