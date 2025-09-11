package com.example.playlist_screen.screen

sealed interface PlaylistScreenIntent {
    // Auth & data
    data class FetchAccessToken(val token: String): PlaylistScreenIntent
    data object RefreshTracks: PlaylistScreenIntent
    data class SetPlaylistUrn(val urn: String): PlaylistScreenIntent

    // Ux state
    data object ChangeDidVibrate: PlaylistScreenIntent
}