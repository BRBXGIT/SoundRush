package com.example.home_screen.screen

data class HomeScreenState(
    val accessToken: String? = null,
    val refreshTrigger: Int = 0,

    val isCreatePlaylistBSVisible: Boolean = false,
    val playlistName: String = "",
    val playlistDescription: String = "",

    val didVibrate: Boolean = false,

    val isInDeleteMode: Boolean = false,
    val playlistsUrnsForDelete: Map<String, String> = emptyMap()
)