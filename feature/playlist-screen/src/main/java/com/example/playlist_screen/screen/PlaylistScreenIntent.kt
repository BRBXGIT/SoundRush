package com.example.playlist_screen.screen

sealed class PlaylistScreenIntent {
    data class UpdateScreenState(val state: PlaylistScreenState): PlaylistScreenIntent()

    data object FetchTokens: PlaylistScreenIntent()

    data class FetchPlaylist(
        val onUnauthorized: () -> Unit
    ): PlaylistScreenIntent()
}