package com.example.home_screen.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.bars.navBarBottomPadding
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.common.utils.PagingErrorContainer
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.theme.mColors
import com.example.home_screen.sections.CreatePlaylistBS
import com.example.home_screen.sections.CreatePlaylistFab
import com.example.home_screen.sections.HomeScreenTopBar
import com.example.home_screen.sections.PlaylistsLVG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    commonVM: CommonVM,
    viewModel: HomeScreenVM
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    val accessToken = commonState.accessToken
    LaunchedEffect(accessToken) {
        if (accessToken != null) {
            viewModel.sendIntent(HomeScreenIntent.FetchAccessToken(accessToken))
        }
    }

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)
    val snackbarScope = rememberCoroutineScope()

    // Paging stuff
    val playlists = viewModel.playlists.collectAsLazyPagingItems()
    PagingErrorContainer(
        items = playlists,
        onUnauthorized = { commonVM.sendIntent(CommonIntent.RefreshTokens) },
        onRetryRequest = { label, retry ->
            snackbarScope.launch { sendRetrySnackbar(label, retry) }
        }
    )

    val isLoading = (commonState.isLoading) or (playlists.loadState.refresh is LoadState.Loading)
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            HomeScreenTopBar(
                isLoading = isLoading,
                scrollBehavior = topBarScrollBehavior
            )
        },
        floatingActionButton = {
            CreatePlaylistFab(
                onClick = { viewModel.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility) }
            )
        },
        contentWindowInsets = WindowInsets(bottom = navBarBottomPadding()),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (screenState.isCreatePlaylistBSVisible) {
            CreatePlaylistBS(
                screenState = screenState,
                onCreateClick = { viewModel.sendIntent(HomeScreenIntent.CreatePlaylist { playlists.refresh() }) },
                onDismissRequest = { viewModel.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility) },
                onPlaylistNameChange = { viewModel.sendIntent(HomeScreenIntent.ChangePlaylistName(it)) },
                onPlaylistDescriptionChange = { viewModel.sendIntent(HomeScreenIntent.ChangePlaylistDescription(it)) },
            )
        }

        PullToRefreshBox(
            indicator = {},
            isRefreshing = isLoading,
            onRefresh = { playlists.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = navBarBottomPadding()
                )
        ) {
            PlaylistsLVG(
                playlists = playlists,
                onCardClick = {},
            )
        }
    }
}