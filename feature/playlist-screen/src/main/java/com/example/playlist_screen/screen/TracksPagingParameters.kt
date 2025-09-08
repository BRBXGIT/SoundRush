package com.example.playlist_screen.screen

data class TracksPagingParameters(
    val accessToken: String? = null,
    val playlistUrn: String = "",
    val refreshTrigger: Int = 0
)