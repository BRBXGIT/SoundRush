package com.example.home_screen.screen

data class HomeScreenState(
    val accessToken: String? = null,

    val isCreatePlaylistBSVisible: Boolean = false,
    val playlistName: String = "",
    val playlistDescription: String = ""
)
