package com.example.navbar_screens.user_playlists_screen.sections

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.design_system.theme.SoundRushIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPlaylistsScreenTopBar(
    onSearchClick: () -> Unit,
    onPlusClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "My playlists"
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.Magnifier),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = onPlusClick
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.PlusCircle),
                    contentDescription = null
                )
            }
        }
    )
}