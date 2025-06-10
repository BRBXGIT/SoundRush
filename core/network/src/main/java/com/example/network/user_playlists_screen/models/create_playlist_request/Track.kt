package com.example.network.user_playlists_screen.models.create_playlist_request


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id")
    val id: Int = 1
)