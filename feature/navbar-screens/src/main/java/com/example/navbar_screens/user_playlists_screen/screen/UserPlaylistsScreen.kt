package com.example.navbar_screens.user_playlists_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import kotlinx.coroutines.launch

@Composable
fun UserPlaylistsScreen(
    viewModel: UserPlaylistsScreenVM,
    screenState: UserPlaylistsScreenState
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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                text = playlists.itemCount.toString()
            )
        }
    }

    LaunchedEffect(playlists.loadState.refresh) {
        if (playlists.loadState.refresh is LoadState.Error) {
            val error = (playlists.loadState.refresh as LoadState.Error).error as NetworkException
            if (error.error == NetworkErrors.UNAUTHORIZED) {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = UserPlaylistsScreenUtils.REFRESHING_TOKENS_TEXT,
                    )
                )
                viewModel.sendIntent(
                    UserPlaylistsScreenIntent.RefreshUserTokens(
                        onComplete = { playlists.retry() }
                    )
                )
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

        UserPlaylistsScreen(
            viewModel = userPlaylistsScreenVM,
            screenState = userPlaylistsScreenState
        )
    }
}