package com.example.playlist_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.nav_bar.calculateNavBarBottomPadding
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.common.utils.HandleAccessToken
import com.example.common.utils.PagingErrorContainer
import com.example.design_system.bars.TopBarWithLoadingIndicator
import com.example.design_system.cards.track_card.TrackCard
import com.example.design_system.containers.paginated_tracks_container.PaginatedTracksContainer
import com.example.design_system.containers.vibrating_spacer.VibratingSpacer
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.theme.mColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    navController: NavController,
    playlistUrn: String,
    playlistName: String,
    commonVM: CommonVM,
) {
    val viewModel = hiltViewModel<PlaylistScreenVM>()
    val screenState by viewModel.playlistScreenState.collectAsStateWithLifecycle()

    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val tracks = viewModel.tracks.collectAsLazyPagingItems()

    val isLoading = commonState.isLoading || tracks.loadState.refresh is LoadState.Loading

    HandleAccessToken(
        accessToken = commonState.accessToken,
        onHandle = {
            viewModel.sendIntent(PlaylistScreenIntent.SetPlaylistUrn(playlistUrn))
            viewModel.sendIntent(PlaylistScreenIntent.FetchAccessToken(it))
        }
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    SnackbarObserver(snackbarHostState)

    PagingErrorContainer(
        items = tracks,
        onUnauthorized = { commonVM.sendIntent(CommonIntent.RefreshTokens) },
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        }
    )

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(bottom = calculateNavBarBottomPadding()),
        topBar = { TopBarWithLoadingIndicator(playlistName, isLoading, topBarScrollBehavior) },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        val pullToRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            state = pullToRefreshState,
            indicator = {},
            onRefresh = { viewModel.sendIntent(PlaylistScreenIntent.RefreshTracks) },
            isRefreshing = isLoading,
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = calculateNavBarBottomPadding()
                )
        ) {
            VibratingSpacer(
                didVibrate = screenState.didVibrate,
                distance = pullToRefreshState.distanceFraction,
                onVibrateChange = { viewModel.sendIntent(PlaylistScreenIntent.ChangeDidVibrate) }
            ) {
                PaginatedTracksContainer {
                    items(tracks.itemCount) { index ->
                        val current = tracks[index]

                        current?.let {
                            TrackCard(
                                posterPath = current.artworkUrl,
                                name = current.title,
                                author = current.user.username,
                                duration = current.duration
                            )
                        }
                    }
                }
            }
        }
    }
}