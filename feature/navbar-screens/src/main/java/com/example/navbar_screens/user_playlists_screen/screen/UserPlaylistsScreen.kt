package com.example.navbar_screens.user_playlists_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.CommonIntent
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.navbar_screens.common.BottomNavBar
import com.example.navbar_screens.user_playlists_screen.sections.PlaylistsLC
import com.example.navbar_screens.user_playlists_screen.sections.UserPlaylistsScreenTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPlaylistsScreen(
    commonVM: CommonVM,
    viewModel: UserPlaylistsScreenVM,
    screenState: UserPlaylistsScreenState,
    commonState: CommonState,
    navController: NavController
) {
    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    val playlists = viewModel.playlists.collectAsLazyPagingItems()

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            UserPlaylistsScreenTopBar(
                scrollBehavior = topBarScrollBehavior,
                onSearchClick = {},
                onPlusClick = {},
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItemIndex = commonState.chosenNavBarIndex,
                onNavItemClick = { index, route ->
                    commonVM.sendIntent(
                        CommonIntent.UpdateCommonState(
                            commonState.copy(
                                chosenNavBarIndex = index
                            )
                        )
                    )
                    navController.navigate(route)
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isRefreshing = (commonState.isUserTokensRefreshing) or
                    (playlists.loadState.refresh is LoadState.Loading)

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { playlists.refresh() },
            ) {
                PlaylistsLC(playlists)
            }
        }
    }

    LaunchedEffect(playlists.loadState.refresh) {
        if (playlists.loadState.refresh is LoadState.Error) {
            val error = (playlists.loadState.refresh as LoadState.Error).error as NetworkException
            if (error.error == NetworkErrors.UNAUTHORIZED) {
                if (!commonState.isUserTokensRefreshing) {
                    commonVM.sendIntent(
                        CommonIntent.RefreshUserTokens(
                            refreshToken = screenState.refreshToken!!,
                            onComplete = {
                                viewModel.sendIntent(UserPlaylistsScreenIntent.FetchUserTokens)
                                playlists.refresh()
                            }
                        )
                    )
                }
            } else {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = error.label,
                        action = SnackbarAction(
                            name = UserPlaylistsScreenUtils.REFRESH_TEXT,
                            action = { playlists.refresh() }
                        )
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun UserPlaylistsScreenPreview() {
    SoundRushTheme {
        val userPlaylistsScreenVM = hiltViewModel<UserPlaylistsScreenVM>()
        val userPlaylistsScreenState by userPlaylistsScreenVM.userPlaylistsScreenState.collectAsStateWithLifecycle()
        val commonVM = hiltViewModel<CommonVM>()
        val commonState by commonVM.commonState.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        UserPlaylistsScreen(
            commonVM = commonVM,
            viewModel = userPlaylistsScreenVM,
            screenState = userPlaylistsScreenState,
            commonState = commonState,
            navController = navController
        )
    }
}