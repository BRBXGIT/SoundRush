package com.example.design_system.cards.playlist_card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.cards.SoundRushAsyncImage
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun PlaylistCard(
    posterPath: String?,
    title: String,
    trackCount: Int,
    creator: String,
    showBorder: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .width(PlaylistCardUtils.PLAYLIST_CARD_SIZE.dp)
            .clip(mShapes.small)
            .clickable(onClick = onClick)
            .background(mColors.surfaceContainerLow)
            .then(
                other = if (showBorder) {
                    Modifier.border(
                        width = 2.dp,
                        color = mColors.tertiary,
                        shape = mShapes.small
                    )
                } else {
                    Modifier
                }
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(PlaylistCardUtils.PLAYLIST_CARD_SIZE.dp)
                .clip(mShapes.small)
        ) {
            SoundRushAsyncImage(posterPath)
        }

        Text(
            text = title,
            style = mTypography.bodyLarge.copy(fontWeight = FontWeight.W700)
        )
        
        Text(
            text = "Playlist • $creator • $trackCount tracks",
            style = mTypography.labelMedium.copy(color = mColors.secondary)
        )
    }
}

@Preview
@Composable
private fun PlaylistCardPreview() {
    SoundRushTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
        ) {
            PlaylistCard(
                posterPath = "",
                title = "Playlist",
                trackCount = 228,
                creator = "BRBX",
                onClick = {},
                showBorder = false,
            )
        }
    }
}