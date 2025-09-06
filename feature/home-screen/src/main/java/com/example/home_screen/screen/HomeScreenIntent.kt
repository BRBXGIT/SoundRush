package com.example.home_screen.screen

sealed interface HomeScreenIntent {
    // Auth & data
    data class FetchAccessToken(val token: String) : HomeScreenIntent
    data object RefreshPlaylists : HomeScreenIntent

    // Playlist creation
    data object ChangeCreatePlaylistBSVisibility : HomeScreenIntent
    data class ChangePlaylistName(val name: String) : HomeScreenIntent
    data class ChangePlaylistDescription(val description: String) : HomeScreenIntent
    data object CreatePlaylist : HomeScreenIntent

    // UX state
    data class ChangeDidVibrate(val didVibrate: Boolean) : HomeScreenIntent

    // Playlist deletion
    data object ChangeIsInDeleteMode : HomeScreenIntent
    data class AddUrnToDeleteList(val urn: String, val playlistName: String) : HomeScreenIntent
    data class RemoveUrnFromList(val urn: String) : HomeScreenIntent
    data object DeletePlaylists : HomeScreenIntent
}