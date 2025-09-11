package com.example.playlist_screen.sections

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.mColors

object PlayFabConstants {
    const val PLAY_FAB_TEST_TAG = "PlayFabTestTag"
}

@Composable
fun PlayFab(
    onClick: () -> Unit,
    isPlaying: Boolean
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.testTag(PlayFabConstants.PLAY_FAB_TEST_TAG)
    ) {
        val animatedImage = AnimatedImageVector.animatedVectorResource(SoundRushIcons.PlayPauseAnimated)
        val animatedPainter = rememberAnimatedVectorPainter(
            animatedImageVector = animatedImage,
            atEnd = isPlaying
        )

        Image(
            painter = animatedPainter,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            colorFilter = ColorFilter.tint(mColors.onPrimaryContainer)
        )
    }
}