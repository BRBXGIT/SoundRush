package com.example.network.playlist_screen.models


import com.google.gson.annotations.SerializedName

data class PlaylistTracksResponse(
    @SerializedName("collection")
    val collection: List<Collection> = listOf(),
    @SerializedName("next_href")
    val nextHref: String? = null
)