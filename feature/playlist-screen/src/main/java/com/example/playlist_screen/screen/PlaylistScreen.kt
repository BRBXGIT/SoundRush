package com.example.playlist_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.common.bars.calculateNavBarBottomPadding
import com.example.common.state.CommonVM
import com.example.design_system.theme.mColors

@Composable
fun PlaylistScreen(
    navController: NavController,
    playlistUrn: String,
    commonVM: CommonVM
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(bottom = calculateNavBarBottomPadding()),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = calculateNavBarBottomPadding()
                )
        ) {
            Text(
                text = "Playlist screen"
            )
        }
    }
}