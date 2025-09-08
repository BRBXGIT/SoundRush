package com.example.design_system.containers.vibrating_spacer

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VibratingSpacer(
    didVibrate: Boolean,
    distance: Float,
    onVibrateChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        val animatedPullToRefresh by animateDpAsState((distance * 8).dp)

        Spacer(Modifier.height(animatedPullToRefresh))

        CreateVibration(
            distance = distance,
            didVibrate = didVibrate,
            onVibrateChange = { onVibrateChange(it) }
        )

        content()
    }
}