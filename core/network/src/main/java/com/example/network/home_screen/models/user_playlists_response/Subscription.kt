package com.example.network.home_screen.models.user_playlists_response


import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("product")
    val product: Product = Product()
)