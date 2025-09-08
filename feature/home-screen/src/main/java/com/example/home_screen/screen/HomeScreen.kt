package com.example.home_screen.screen

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.nav_bar.calculateNavBarBottomPadding
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.common.utils.HandleAccessToken
import com.example.common.utils.PagingErrorContainer
import com.example.design_system.bars.TopBarWithLoadingIndicator
import com.example.design_system.containers.vibrating_spacer.VibratingSpacer
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.theme.mColors
import com.example.home_screen.sections.CreatePlaylistBS
import com.example.home_screen.sections.FloatingToolBar
import com.example.home_screen.sections.PlaylistsLVG
import com.example.network.home_screen.models.user_playlists_response.Collection
import com.example.playlist_screen.navigation.PlaylistScreenRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    commonVM: CommonVM,
    viewModel: HomeScreenVM
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val playlists = viewModel.playlists.collectAsLazyPagingItems()

    HandleAccessToken(
        accessToken = commonState.accessToken,
        onHandle = { viewModel.sendIntent(HomeScreenIntent.FetchAccessToken(it)) }
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    SnackbarObserver(snackbarHostState)

    PagingErrorContainer(
        items = playlists,
        onUnauthorized = { commonVM.sendIntent(CommonIntent.RefreshTokens) },
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        }
    )

    val isLoading = commonState.isLoading || playlists.loadState.refresh is LoadState.Loading
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { TopBarWithLoadingIndicator("Playlists", isLoading, topBarScrollBehavior) },
        floatingActionButton = { FloatingToolBar(screenState, viewModel) },
        contentWindowInsets = WindowInsets(bottom = calculateNavBarBottomPadding()),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->

        if (screenState.isCreatePlaylistBSVisible) {
            CreatePlaylistBS(
                screenState = screenState,
                viewModel = viewModel,
            )
        }

        PullToRefreshContent(
            isLoading = isLoading,
            innerPadding = innerPadding,
            screenState = screenState,
            playlists = playlists,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshContent(
    isLoading: Boolean,
    innerPadding: PaddingValues,
    screenState: HomeScreenState,
    playlists: LazyPagingItems<Collection>,
    viewModel: HomeScreenVM,
    navController: NavController
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = { viewModel.sendIntent(HomeScreenIntent.RefreshPlaylists) },
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
            onVibrateChange = { viewModel.sendIntent(HomeScreenIntent.ChangeDidVibrate(it)) }
        ) {
            PlaylistsLVG(
                selectedPlaylistsUrns = screenState.playlistsUrnsForDelete.keys,
                playlists = playlists,
                isInDeleteMode = screenState.isInDeleteMode,
                onCardClick = { urn, name ->
                    if (screenState.isInDeleteMode) {
                        if (urn in screenState.playlistsUrnsForDelete) {
                            viewModel.sendIntent(HomeScreenIntent.RemoveUrnFromList(urn))
                        } else {
                            viewModel.sendIntent(HomeScreenIntent.AddUrnToDeleteList(urn, name))
                        }
                    } else {
                        navController.navigate(PlaylistScreenRoute(urn, name))
                    }
                },
            )
        }
    }
}