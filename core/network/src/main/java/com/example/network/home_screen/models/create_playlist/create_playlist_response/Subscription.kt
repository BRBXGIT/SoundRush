package com.example.network.home_screen.models.create_playlist.create_playlist_response


import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("product")
    val product: Product = Product()
)