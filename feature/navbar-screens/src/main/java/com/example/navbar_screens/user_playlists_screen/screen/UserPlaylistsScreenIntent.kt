package com.example.navbar_screens.user_playlists_screen.screen

sealed class UserPlaylistsScreenIntent {
    data class RefreshUserTokens(
        val onComplete: () -> Unit
    ): UserPlaylistsScreenIntent()
}