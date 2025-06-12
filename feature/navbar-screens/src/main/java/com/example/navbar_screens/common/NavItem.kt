package com.example.navbar_screens.common

import com.example.design_system.theme.SoundRushIcons
import com.example.navbar_screens.settings_screen.navigation.SettingsScreenRoute
import com.example.navbar_screens.user_likes_screen.navigation.UserLikesScreenRoute
import com.example.navbar_screens.user_playlists_screen.navigation.UserPlaylistsScreenRoute

data class NavItem(
    val label: String,
    val icon: Int, // Icon already animated from filled to outlined
    val destination: Any
)

val navItems = listOf(
    NavItem(
        label = "Playlists",
        icon = SoundRushIcons.PlaylistAnimated,
        destination = UserPlaylistsScreenRoute
    ),
    NavItem(
        label = "Favourites",
        icon = SoundRushIcons.HeartAnimated,
        destination = UserLikesScreenRoute
    ),
    NavItem(
        label = "Settings",
        icon = SoundRushIcons.GearAnimated,
        destination = SettingsScreenRoute
    ),
)