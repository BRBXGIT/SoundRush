package com.example.playlist_screen.screen

import com.example.network.playlist_screen.models.PlaylistResponse

data class PlaylistScreenState(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val isLoading: Boolean = false,

    val playlistId: Int = 0,
    val playlist: PlaylistResponse? = null
)