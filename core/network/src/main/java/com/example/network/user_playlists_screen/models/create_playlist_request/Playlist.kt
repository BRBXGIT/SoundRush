package com.example.network.user_playlists_screen.models.create_playlist_request

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("sharing")
    val sharing: String = "public",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("tracks")
    val tracks: List<Track> = listOf(Track())
)