package com.example.home_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.common.utils.PagingErrorContainer
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.theme.mColors
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    commonVM: CommonVM,
    viewModel: HomeScreenVM
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

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

    // Screen
    val screenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(playlists.itemCount) {
                    Text(it.toString())
                }
            }
        }
    }
}