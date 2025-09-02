package com.example.home_screen.screen

sealed interface HomeScreenIntent {
    data class FetchAccessToken(val token: String): HomeScreenIntent

    data object ChangeCreatePlaylistBSVisibility: HomeScreenIntent
    data class ChangePlaylistName(val name: String): HomeScreenIntent
    data class ChangePlaylistDescription(val description: String): HomeScreenIntent
    data class CreatePlaylist(val onComplete: () -> Unit): HomeScreenIntent

    data class ChangeDidVibrate(val didVibrate: Boolean): HomeScreenIntent
}