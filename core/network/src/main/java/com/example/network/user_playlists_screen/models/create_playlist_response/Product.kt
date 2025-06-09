package com.example.network.user_playlists_screen.models.create_playlist_response


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)