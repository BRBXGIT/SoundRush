package com.example.design_system.playlist_item

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun PlaylistItem(
    imageUrl: String,
    playlistName: String,
    playlistAuthor: String,
    tracksAmount: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(mShapes.extraSmall)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .background(
                    color = mColors.surfaceVariant,
                    shape = mShapes.extraSmall
                )
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(68.dp, 68.dp)
                    .clip(mShapes.extraSmall),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer(68.dp, 68.dp) }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = playlistName,
                style = mTypography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = playlistAuthor,
                style = mTypography.bodyMedium
            )

            Text(
                text = "Playlist $tracksAmount tracks",
                style = mTypography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun PlaylistItemPreview() {
    SoundRushTheme {
        PlaylistItem(
            imageUrl = "",
            playlistName = "Phonk",
            playlistAuthor = "BRBX",
            tracksAmount = 123,
            onClick = {},
            onLongClick = {},
        )
    }
}