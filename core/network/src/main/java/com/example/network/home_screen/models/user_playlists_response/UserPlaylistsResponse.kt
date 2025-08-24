package com.example.network.home_screen.models.user_playlists_response


import com.google.gson.annotations.SerializedName

data class UserPlaylistsResponse(
    @SerializedName("collection")
    val collection: List<Collection> = listOf(),
    @SerializedName("next_href")
    val nextHref: String = ""
)