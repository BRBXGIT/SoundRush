package com.example.playlist_screen.screen

data class PlaylistScreenState(
    // Auth & data
    val accessToken: String? = null,
    val refreshTrigger: Int = 0,
    val playlistUrn: String = "",

    // Ux state
    val didVibrate: Boolean = false,

    // Ui state
    val isPlaying: Boolean = false
)