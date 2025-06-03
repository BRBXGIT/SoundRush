package com.example.data.domain

import com.example.local.datastore.auth.AuthState
import com.example.network.auth.models.UserTokensResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthRepo {

    val accessTokenFlow: Flow<String?>

    val authState: Flow<AuthState>

    suspend fun saveAccessToken(token: String)

    suspend fun getUserTokens(
        grantType: String = "authorization_code",
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        code: String
    ): Response<UserTokensResponse>
}