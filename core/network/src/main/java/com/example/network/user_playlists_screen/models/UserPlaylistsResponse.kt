package com.example.network.user_playlists_screen.models


import com.google.gson.annotations.SerializedName

data class UserPlaylistsResponse(
    @SerializedName("collection")
    val collection: List<Collection> = listOf(),
    @SerializedName("next_href")
    val nextHref: String = ""
)