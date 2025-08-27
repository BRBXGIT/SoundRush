package com.example.common.bars

import com.example.design_system.theme.SoundRushIcons

data class NavItem(
    val icon: Int, // Icon already animated from outlined to filled
    val label: String,
    val destination: Any
)

val navItems = listOf(
    NavItem(
        icon = SoundRushIcons.PlaylistsAnimated,
        label = "Playlists",
        destination = ""
    ),
    NavItem(
        icon = SoundRushIcons.HeartAnimated,
        label = "Likes",
        destination = ""
    ),
    NavItem(
        icon = SoundRushIcons.SettingsAnimated,
        label = "Settings",
        destination = ""
    )
)