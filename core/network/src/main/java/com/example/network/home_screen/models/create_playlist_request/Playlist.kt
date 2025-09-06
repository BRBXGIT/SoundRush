package com.example.network.home_screen.models.create_playlist_request


import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("sharing")
    val sharing: String = "private",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("tracks")
    val tracks: List<Track> = emptyList()
)