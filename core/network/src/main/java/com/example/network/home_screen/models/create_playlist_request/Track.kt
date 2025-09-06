package com.example.network.home_screen.models.create_playlist_request


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("urn")
    val urn: String = ""
)