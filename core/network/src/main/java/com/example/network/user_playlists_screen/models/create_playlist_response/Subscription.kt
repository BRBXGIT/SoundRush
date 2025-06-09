package com.example.network.user_playlists_screen.models.create_playlist_response


import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("product")
    val product: Product = Product()
)