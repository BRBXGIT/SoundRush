package com.example.network.auth.models


import com.google.gson.annotations.SerializedName

data class TokensResponse(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("expires_in")
    val expiresIn: Int = 0,
    @SerializedName("refresh_token")
    val refreshToken: String = "",
    @SerializedName("scope")
    val scope: String = "",
    @SerializedName("token_type")
    val tokenType: String = ""
)