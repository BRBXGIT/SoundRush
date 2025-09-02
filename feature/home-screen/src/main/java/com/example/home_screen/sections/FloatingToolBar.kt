package com.example.home_screen.sections

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.design_system.theme.SoundRushIcons

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingToolBar(
    onFabClick: () -> Unit
) {
    HorizontalFloatingToolbar(
        expanded = true,
        floatingActionButton = { CreatePlaylistFab(onClick = onFabClick) },
        content = {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(SoundRushIcons.BinFilled),
                    contentDescription = ""
                )
            }
        },
    )
}