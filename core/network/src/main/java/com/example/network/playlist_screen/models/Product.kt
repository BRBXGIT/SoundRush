package com.example.network.playlist_screen.models


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)