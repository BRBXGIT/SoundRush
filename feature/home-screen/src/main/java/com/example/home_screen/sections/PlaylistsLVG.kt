package com.example.home_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.design_system.cards.CardsUtils
import com.example.design_system.cards.PlaylistCard
import com.example.network.home_screen.models.user_playlists_response.Collection

@Composable
fun PlaylistsLVG(
    playlists: LazyPagingItems<Collection>,
    onCardClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(CardsUtils.PLAYLIST_CARD_SIZE.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(playlists.itemCount) { index ->
            val current = playlists[index]

            current?.let {
                PlaylistCard(
                    posterPath = current.artworkUrl,
                    title = current.title,
                    trackCount = current.trackCount,
                    creator = current.user.fullName,
                    onClick = onCardClick
                )
            }
        }
    }
}