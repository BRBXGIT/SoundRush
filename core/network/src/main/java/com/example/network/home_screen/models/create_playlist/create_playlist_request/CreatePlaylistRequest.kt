package com.example.network.home_screen.models.create_playlist.create_playlist_request


import com.google.gson.annotations.SerializedName

data class CreatePlaylistRequest(
    @SerializedName("playlist")
    val playlist: Playlist = Playlist()
)