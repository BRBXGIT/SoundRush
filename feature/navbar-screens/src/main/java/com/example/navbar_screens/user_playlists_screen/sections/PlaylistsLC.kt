package com.example.navbar_screens.user_playlists_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.playlist_item.PlaylistItem
import com.example.design_system.theme.SoundRushTheme
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenUtils
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenVM
import com.example.network.user_playlists_screen.models.user_playlists_response.Collection

@Composable
fun PlaylistsLC(
    playlists: LazyPagingItems<Collection>,
    onPlaylistClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = UserPlaylistsScreenUtils.PLAYLISTS_LC_VERTICAL_PADDING.dp),
        verticalArrangement = Arrangement.spacedBy(UserPlaylistsScreenUtils.PLAYLISTS_LC_SPACER.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(playlists.itemCount) { index ->
            val playlist = playlists[index]

            playlist?.let {
                PlaylistItem(
                    imageUrl = playlist.artworkUrl,
                    playlistName = playlist.title,
                    playlistAuthor = playlist.user.fullName,
                    tracksAmount = playlist.trackCount,
                    onClick = {
                        onPlaylistClick(playlist.id)
                    },
                    onLongClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
fun PlaylistsLCPreview() {
    SoundRushTheme {
        val userPlaylistsScreenVM = hiltViewModel<UserPlaylistsScreenVM>()
        val playlists = userPlaylistsScreenVM.playlists.collectAsLazyPagingItems()

        PlaylistsLC(
            playlists = playlists,
            onPlaylistClick = {}
        )
    }
}