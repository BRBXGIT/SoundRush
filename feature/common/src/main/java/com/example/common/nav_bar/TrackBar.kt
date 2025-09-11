package com.example.common.nav_bar

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.cards.SoundRushAsyncImage
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

object TrackBarConstants {
    const val PLAY_PAUSE_TEST_TAG = "PlayPauseTestTag"
}

@Composable
fun TrackBar(
    posterPath: String?,
    author: String,
    name: String,
    isPlaying: Boolean,
    onPlayClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mColors.surfaceContainer)
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TrackInfo(
            posterPath = posterPath,
            name = name,
            author = author
        )

        PlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayClick
        )
    }
}

@Composable
private fun RowScope.TrackInfo(
    posterPath: String?,
    name: String,
    author: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.weight(1f)
    ) {
        PosterImage(posterPath)

        Column {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = mTypography.bodyMedium.copy(fontWeight = FontWeight.W700, color = mColors.onSurface)
            )
            Text(
                text = author,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = mTypography.bodyMedium.copy(color = mColors.secondary),
            )
        }
    }
}

@Composable
private fun PosterImage(posterPath: String?) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(mShapes.extraSmall),
        contentAlignment = Alignment.Center
    ) {
        SoundRushAsyncImage(posterPath)
    }
}

@Composable
private fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    val animatedImage = AnimatedImageVector.animatedVectorResource(SoundRushIcons.PlayPauseAnimated)
    val animatedPainter = rememberAnimatedVectorPainter(
        animatedImageVector = animatedImage,
        atEnd = !isPlaying
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier.testTag(TrackBarConstants.PLAY_PAUSE_TEST_TAG)
    ) {
        Image(
            painter = animatedPainter,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            colorFilter = ColorFilter.tint(mColors.onSurface)
        )
    }
}

@Preview
@Composable
fun TrackBarPreview() {
    SoundRushTheme {
        TrackBar(
            posterPath = "",
            author = "ROCKET",
            name = "LUV",
            isPlaying = false,
            onPlayClick = {}
        )
    }
}