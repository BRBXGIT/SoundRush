package com.example.common.nav_bar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun calculateNavBarBottomPadding(): Dp {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    return BarsUtils.NAV_BAR_HEIGHT.dp + bottomPadding
}