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
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenVM
import com.example.network.user_playlists_screen.models.user_playlists_response.Collection

@Composable
fun PlaylistsLC(
    playlists: LazyPagingItems<Collection>
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(playlists.itemCount) { index ->
            val playlists = playlists[index]

            playlists?.let {
                PlaylistItem(
                    imageUrl = playlists.artworkUrl,
                    playlistName = playlists.title,
                    playlistAuthor = playlists.user.fullName,
                    tracksAmount = playlists.trackCount,
                    onClick = {},
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

        PlaylistsLC(playlists)
    }
}