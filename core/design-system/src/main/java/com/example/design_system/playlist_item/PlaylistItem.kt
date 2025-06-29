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
import com.example.design_system.theme.CommonDimens
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.design_system.utils.AnimatedShimmer

@Composable
fun PlaylistItem(
    posterPath: String?,
    playlistName: String,
    playlistAuthor: String,
    tracksAmount: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PlaylistItemDimens.IMAGE_INFO_SPACER.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonDimens.HORIZONTAL_PADDING.dp)
            .clip(mShapes.extraSmall)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(PlaylistItemDimens.MAIN_ROW_PADDING.dp)
    ) {
        Box(
            modifier = Modifier
                .size(PlaylistItemDimens.IMAGE_SIZE.dp)
                .background(
                    color = mColors.surfaceVariant,
                    shape = mShapes.extraSmall
                )
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterPath)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(PlaylistItemDimens.IMAGE_SIZE.dp, PlaylistItemDimens.IMAGE_SIZE.dp)
                    .clip(mShapes.extraSmall),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = {
                    AnimatedShimmer(
                        PlaylistItemDimens.IMAGE_SIZE.dp,
                        PlaylistItemDimens.IMAGE_SIZE.dp
                    )
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(PlaylistItemDimens.INFO_COLUMN_SPACER.dp)
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
                text = "Playlist • $tracksAmount tracks",
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
            posterPath = "",
            playlistName = "Phonk",
            playlistAuthor = "BRBX",
            tracksAmount = 123,
            onClick = {},
            onLongClick = {},
        )
    }
}