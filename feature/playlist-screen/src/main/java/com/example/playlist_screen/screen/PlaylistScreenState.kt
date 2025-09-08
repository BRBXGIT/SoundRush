package com.example.playlist_screen.screen

data class PlaylistScreenState(
    val accessToken: String? = null,
    val refreshTrigger: Int = 0,
    val playlistUrn: String = "",

    val didVibrate: Boolean = false
)