package com.example.network.home_screen.models.create_playlist.create_playlist_response


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)