package com.example.network.auth.api

import com.example.network.auth.models.TokensResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiInstance {

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getTokens(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("code") code: String
    ): Response<TokensResponse>
}