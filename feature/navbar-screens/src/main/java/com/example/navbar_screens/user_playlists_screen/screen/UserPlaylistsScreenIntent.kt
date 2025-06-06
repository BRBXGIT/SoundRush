package com.example.navbar_screens.user_playlists_screen.screen

sealed class UserPlaylistsScreenIntent {
    data object RefreshUserTokens: UserPlaylistsScreenIntent()
}