package com.example.navbar_screens.user_playlists_screen.screen

data class UserPlaylistsScreenState(
    val accessToken: String? = null,
    val refreshToken: String? = null,

    val isLoading: Boolean = false,

    val isCreatePlaylistBSOpen: Boolean = false,
    val newPlaylistTitle: String = "",
    val newPlaylistDescription: String = ""
)
