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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
                    text = "Playlists"
                )
            }
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300)),
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearWavyProgressIndicator()
        }
    }
}