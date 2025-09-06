package com.example.home_screen.sections

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.design_system.theme.SoundRushIcons
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingToolBar(
    screenState: HomeScreenState,
    viewModel: HomeScreenVM
) {
    HorizontalFloatingToolbar(
        expanded = true,
        floatingActionButton = {
            CreatePlaylistFab(
                onClick = {
                    if (screenState.isInDeleteMode) {
                        // TODO()
                    } else {
                        viewModel.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility)
                    }
                },
                isInDeleteMode = screenState.isInDeleteMode
            )
        },
        content = {
            IconButton(
                onClick = { viewModel.sendIntent(HomeScreenIntent.ChangeIsInDeleteMode) }
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.BinFilled),
                    contentDescription = ""
                )
            }
        },
    )
}