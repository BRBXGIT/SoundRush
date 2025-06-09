package com.example.navbar_screens.user_playlists_screen.screen

sealed class UserPlaylistsScreenIntent {
    data object FetchUserTokens: UserPlaylistsScreenIntent()

    data class UpdateScreenState(val state: UserPlaylistsScreenState): UserPlaylistsScreenIntent()

    data class CreatePlaylist(
        val title: String,
        val description: String,
        val onComplete: () -> Unit,
        val onUnauthorized: () -> Unit
    ): UserPlaylistsScreenIntent()
}