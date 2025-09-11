package com.example.playlist_screen.screen

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.nav_bar.calculateNavBarBottomPadding
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.common.state.Track
import com.example.common.utils.HandleAccessToken
import com.example.common.utils.PagingErrorContainer
import com.example.design_system.bars.TopBarWithLoadingIndicator
import com.example.design_system.cards.track_card.TrackCard
import com.example.design_system.containers.paginated_tracks_container.PaginatedTracksContainer
import com.example.design_system.containers.vibrating_spacer.VibratingSpacer
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.theme.mColors
import com.example.design_system.utils.getHighQualityArtwork
import com.example.design_system.utils.getLowQualityArtwork
import com.example.network.playlist_screen.models.Collection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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

    val isLoading = commonState.isLoading || tracks.loadState.refresh is LoadState.Loading
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopBarWithLoadingIndicator(
                title = playlistName,
                isLoading = isLoading,
                scrollBehavior = topBarScrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(bottom = calculateNavBarBottomPadding()),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        PullToRefreshContent(
            isLoading = isLoading,
            innerPadding = innerPadding,
            screenState = screenState,
            tracks = tracks,
            viewModel = viewModel,
            commonVM = commonVM,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshContent(
    isLoading: Boolean,
    innerPadding: PaddingValues,
    screenState: PlaylistScreenState,
    tracks: LazyPagingItems<Collection>,
    viewModel: PlaylistScreenVM,
    commonVM: CommonVM,
    navController: NavController
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = { viewModel.sendIntent(PlaylistScreenIntent.RefreshTracks) },
        indicator = {},
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
                    tracks[index]?.let { track ->
                        TrackCard(
                            posterPath = getLowQualityArtwork(track.artworkUrl),
                            name = track.title,
                            author = track.user.username,
                            duration = track.duration,
                            onClick = { handleTrackClick(track, tracks, commonVM) }
                        )
                    }
                }
            }
        }
    }
}

@VisibleForTesting
internal fun handleTrackClick(
    track: Collection,
    tracks: LazyPagingItems<Collection>,
    commonVM: CommonVM
) {
    val allTracks = tracks.itemSnapshotList.items.map { item ->
        Track(
            link = item.streamUrl,
            posterPath = getHighQualityArtwork(item.artworkUrl),
            name = item.title,
            author = item.user.username
        )
    }

    val clickedIndex = allTracks.indexOfFirst { it.link == track.streamUrl }
    val queue = if (clickedIndex != -1) allTracks.drop(clickedIndex) else emptyList()

    commonVM.sendIntent(CommonIntent.SetQueue(queue))
    if (queue.isNotEmpty()) {
        commonVM.sendIntent(CommonIntent.SetCurrentTrack(queue.first()))
    }
}