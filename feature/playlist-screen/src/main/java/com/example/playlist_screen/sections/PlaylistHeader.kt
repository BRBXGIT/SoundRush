package com.example.playlist_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.design_system.utils.AnimatedShimmer
import com.example.design_system.theme.CommonDimens
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.playlist_screen.screen.PlaylistScreenDimens
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun PlaylistHeader(
    posterPath: String?,
    playlistName: String,
    tracksAmount: Int,
    playlistDuration: Int,
    createdBy: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlaylistScreenDimens.PLAYLIST_HEADER_ARRANGEMENT.dp),
        modifier = Modifier
            .padding(horizontal = CommonDimens.HORIZONTAL_PADDING.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(PlaylistScreenDimens.PLAYLIST_HEADER_ARRANGEMENT.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp)
                    .background(
                        color = mColors.surfaceVariant,
                        shape = mShapes.extraSmall
                    )
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(posterPath)
                        .crossfade(CommonDimens.ASYNC_IMAGE_CROSS_FADE)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp,
                            PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp
                        )
                        .clip(mShapes.extraSmall),
                    filterQuality = FilterQuality.Low,
                    contentScale = ContentScale.Crop,
                    loading = { AnimatedShimmer(PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp, PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp) }
                )
            }

            Box(
                modifier = Modifier.height(PlaylistScreenDimens.PLAYLIST_IMAGE_SIZE.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(PlaylistScreenDimens.PLAYLIST_INFO_SPACER.dp)
                ) {
                    Text(
                        text = playlistName,
                        style = mTypography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = PlaylistScreenDimens.PLAYLIST_NAME_MAX_LINES,
                        overflow = TextOverflow.Ellipsis
                    )

                    val convertedPlaylistDuration = playlistDuration.toDuration(DurationUnit.MILLISECONDS).inWholeMinutes
                    Text(
                        text = "Playlist • $tracksAmount tracks • $convertedPlaylistDuration min",
                        maxLines = PlaylistScreenDimens.PLAYLIST_INFO_MAX_LINES,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // TODO create link to user
                val createdByAnnotatedString = buildAnnotatedString {
                    append("Created by ")
                    withStyle(
                        style = SpanStyle(
                            color = mColors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(createdBy)
                    }
                }
                Text(
                    text = createdByAnnotatedString,
                    modifier = Modifier.align(Alignment.BottomStart),
                    maxLines = PlaylistScreenDimens.PLAYLIST_CREATED_BY_MAX_LINES,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        HorizontalDivider()
    }
}

@Preview
@Composable
fun PlaylistHeaderPreview() {
    SoundRushTheme {
        PlaylistHeader(
            posterPath = null,
            playlistName = "Name",
            tracksAmount = 13,
            playlistDuration = 122,
            createdBy = "BRBX",
        )
    }
}