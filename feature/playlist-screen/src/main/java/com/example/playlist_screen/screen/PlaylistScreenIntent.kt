package com.example.playlist_screen.screen

sealed interface PlaylistScreenIntent {
    data class FetchAccessToken(val token: String): PlaylistScreenIntent
    data object RefreshTracks: PlaylistScreenIntent
    data class SetPlaylistUrn(val urn: String): PlaylistScreenIntent

    data object ChangeDidVibrate: PlaylistScreenIntent
}