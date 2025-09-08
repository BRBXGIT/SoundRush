package com.example.home_screen.sections

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.design_system.cards.playlist_card.PlaylistCardUtils
import com.example.design_system.cards.playlist_card.PlaylistCard
import com.example.design_system.theme.UiConstants
import com.example.network.home_screen.models.user_playlists_response.Collection

@Composable
fun PlaylistsLVG(
    selectedPlaylistsUrns: Set<String>,
    playlists: LazyPagingItems<Collection>,
    onCardClick: (String, String) -> Unit,
    isInDeleteMode: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(PlaylistCardUtils.PLAYLIST_CARD_SIZE.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(UiConstants.HORIZONTAL_PADDING.dp),
    ) {
        items(
            count = playlists.itemCount,
            key = { index -> playlists[index]?.urn ?: index }
        ) { index ->
            playlists[index]?.let { playlist ->
                PlaylistCard(
                    posterPath = playlist.artworkUrl,
                    title = playlist.title,
                    trackCount = playlist.trackCount,
                    creator = playlist.user.fullName,
                    onClick = { onCardClick(playlist.urn, playlist.title) },
                    showBorder = isInDeleteMode && playlist.urn in selectedPlaylistsUrns,
                )
            }
        }
    }
}