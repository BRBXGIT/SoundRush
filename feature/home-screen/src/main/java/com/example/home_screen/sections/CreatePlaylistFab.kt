package com.example.home_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme

object CreatePlaylistFabConstants {
    const val CREATE_PLAYLIST_FAB_TEST_TAG = "CreatePlaylistFabTestTag"
}

@Composable
fun CreatePlaylistFab(
    onClick: () -> Unit,
    isInDeleteMode: Boolean
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.testTag(CreatePlaylistFabConstants.CREATE_PLAYLIST_FAB_TEST_TAG)
    ) {
        IconContainer(!isInDeleteMode) {
            Icon(
                painter = painterResource(SoundRushIcons.PlusFilled),
                contentDescription = null,
            )
        }

        IconContainer(isInDeleteMode) {
            Icon(
                painter = painterResource(SoundRushIcons.CheckFilled),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun IconContainer(
    visible: Boolean,
    icon: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(300),
            initialOffsetX = { it / 2 }
        ) + fadeIn(tween(300)),
        exit = slideOutHorizontally(
            animationSpec = tween(300),
            targetOffsetX = { it / 2 }
        ) + fadeOut(tween(300))
    ) {
        icon()
    }
}

@Preview
@Composable
private fun CreatePlaylistFabPreview() {
    SoundRushTheme {
        CreatePlaylistFab(
            onClick = {},
            isInDeleteMode = false
        )
    }
}