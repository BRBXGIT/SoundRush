package com.example.home_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.SoundRushTheme

object HomeScreenTopBarConstants {
    const val PLAYLISTS_TEST_TEXT = "Playlists"
    const val PROGRESS_BAR_TEST_TEG = "ProgressBarTestTag"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreenTopBar(
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = HomeScreenTopBarConstants.PLAYLISTS_TEST_TEXT
                )
            }
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearWavyProgressIndicator(modifier = Modifier.testTag(HomeScreenTopBarConstants.PROGRESS_BAR_TEST_TEG))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeScreenTopBarPreview() {
    SoundRushTheme {
        HomeScreenTopBar(
            isLoading = false,
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}