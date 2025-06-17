package com.example.playlist_screen.sections

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreenTopBar(
    playlistName: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = playlistName
            )
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.ArrowLeft),
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlaylistScreenTopBarPreview() {
    SoundRushTheme {
        val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        PlaylistScreenTopBar(
            playlistName = "Phonk",
            scrollBehavior = topBarScrollBehavior,
            onNavIconClick = {}
        )
    }
}