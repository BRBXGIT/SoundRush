package com.example.design_system.cards.track_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.cards.SoundRushAsyncImage
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import java.util.concurrent.TimeUnit

object TrackCardConstants {
    const val SURFACE_TEST_TAG = "SurfaceTestTag"
}

@Composable
fun TrackCard(
    posterPath: String?,
    name: String,
    author: String,
    duration: Int,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = mShapes.small,
        color = mColors.surfaceContainerLow,
        modifier = Modifier.testTag(TrackCardConstants.SURFACE_TEST_TAG)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(mShapes.extraSmall)
            ) {
                SoundRushAsyncImage(posterPath)
            }

            Column {
                Text(
                    text = name,
                    style = mTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val formatDuration = formatDuration(duration)
                Text(
                    style = mTypography.bodyMedium.copy(color = mColors.secondary),
                    text = "$author • $formatDuration",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun formatDuration(milliseconds: Int): String {
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds.toLong())
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong()) -
            TimeUnit.HOURS.toMinutes(hours)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) -
            TimeUnit.HOURS.toSeconds(hours) -
            TimeUnit.MINUTES.toSeconds(minutes)

    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%d:%02d".format(minutes, seconds)
    }
}

@Preview
@Composable
private fun TrackCardPreview() {
    SoundRushTheme {
        TrackCard(
            posterPath = "",
            name = "В миноре (Prod. by Wex & Lawzy)",
            author = "Heronwater",
            duration = 132049,
            onClick = {}
        )
    }
}