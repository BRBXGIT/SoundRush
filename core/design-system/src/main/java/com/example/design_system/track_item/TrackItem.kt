package com.example.design_system.track_item

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.design_system.theme.CommonDimens
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.design_system.utils.AnimatedShimmer

@Composable
fun TrackItem(
    posterPath: String?,
    trackName: String,
    author: String,
    trackDuration: Int,
    streamCount: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(TrackItemDimens.MAIN_ROW_SPACER.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonDimens.HORIZONTAL_PADDING.dp)
            .clip(mShapes.extraSmall)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(TrackItemDimens.MAIN_ROW_PADDING.dp)
    ) {
        Box(
            modifier = Modifier
                .size(TrackItemDimens.IMAGE_SIZE.dp)
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
                    .size(TrackItemDimens.IMAGE_SIZE.dp, TrackItemDimens.IMAGE_SIZE.dp)
                    .clip(mShapes.extraSmall),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = {
                    AnimatedShimmer(
                        TrackItemDimens.IMAGE_SIZE.dp,
                        TrackItemDimens.IMAGE_SIZE.dp
                    )
                }
            )
        }

        val formattedTrackCount = formatNumber(streamCount)
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(TrackItemDimens.IMAGE_SIZE.dp)
        ) {
            Text(
                text = trackName,
                style = mTypography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = author,
                style = mTypography.bodyLarge
            )

            val totalSeconds = trackDuration / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.Play),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "$formattedTrackCount • $minutes:$seconds",
                    style = mTypography.bodyLarge
                )
            }
        }
    }
}

private fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> "${number / 1_000_000}m"
        number >= 1_000 -> "${number / 1_000}k"
        else -> number.toString()
    }
}

@Preview
@Composable
fun TrackItemPreview() {
    SoundRushTheme {
        TrackItem(
            posterPath = "",
            trackName = "ECHO OF TERROR",
            author = "requi3m",
            trackDuration = 120,
            streamCount = 827000,
            onClick = {},
            onLongClick = {}
        )
    }
}