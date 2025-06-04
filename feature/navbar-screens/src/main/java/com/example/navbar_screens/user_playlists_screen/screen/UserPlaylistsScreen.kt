package com.example.navbar_screens.user_playlists_screen.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors

@Composable
fun UserPlaylistsScreen(
    viewModel: UserPlaylistsScreenVM,
    screenState: UserPlaylistsScreenState
) {
    LaunchedEffect(screenState.accessToken) {
        Log.d("CCCC", screenState.accessToken.toString())
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
                text = "User Playlists Screen"
            )
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