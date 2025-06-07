package com.example.data.data

import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.auth.AuthState
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.UserTokensResponse
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import retrofit2.Response

class AuthRepoImpl @Inject constructor(
    private val authManager: AuthManager,
    private val apiInstance: AuthApiInstance
): AuthRepo {

    override val accessTokenFlow = authManager.accessTokenFlow

    override val refreshTokenFlow = authManager.refreshTokenFlow

    override val authState = accessTokenFlow.map { token ->
        if (token.isNullOrBlank()) AuthState.LoggedOut else AuthState.LoggedIn
    }

    override suspend fun saveUserTokens(accessToken: String, refreshToken: String) {
        authManager.saveUserTokens(accessToken, refreshToken)
    }

    override suspend fun getUserTokens(
        grantType: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        codeVerifier: String,
        code: String
    ): Response<UserTokensResponse> {
        return apiInstance.getUserTokens(
            grantType = grantType,
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = redirectUri,
            codeVerifier = codeVerifier,
            code = code
        )
    }

    override suspend fun refreshUserTokens(
        grantType: String,
        clientId: String,
        clientSecret: String,
        refreshToken: String
    ): Response<UserTokensResponse> {
        return apiInstance.refreshTokens(
            grantType = grantType,
            clientId = clientId,
            clientSecret = clientSecret,
            refreshToken = refreshToken
        )
    }
}