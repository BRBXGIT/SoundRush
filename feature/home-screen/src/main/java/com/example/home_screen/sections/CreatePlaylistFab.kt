package com.example.home_screen.sections

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.bars.BarsUtils
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.screen.HomeScreenUtils

@Composable
fun CreatePlaylistFab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.testTag(HomeScreenUtils.CREATE_PLAYLIST_FAB_TEST_TAG)
    ) {
        Icon(
            painter = painterResource(SoundRushIcons.PlusFilled),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
fun CreatePlaylistFabPreview() {
    SoundRushTheme {
        CreatePlaylistFab(
            onClick = {}
        )
    }
}