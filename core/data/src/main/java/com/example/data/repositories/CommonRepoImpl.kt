package com.example.data.repositories

import com.example.data.domain.CommonRepo
import com.example.data.utils.AuthUtils
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkRequest
import com.example.network.common.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CommonRepoImpl @Inject constructor(
    private val authApiInstance: AuthApiInstance,
    private val authManager: AuthManager
): CommonRepo {

    override val accessToken = authManager.accessTokenFlow

    override val refreshToken = authManager.refreshTokenFlow

    override suspend fun refreshTokens(): NetworkResponse<TokensResponse> = NetworkRequest.exec {
        authApiInstance.refreshTokens(
            clientId = AuthUtils.CLIENT_ID,
            clientSecret = AuthUtils.CLIENT_SECRET,
            refreshToken = refreshToken.first()!!
        )
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        authManager.saveAccessToken(accessToken)
        authManager.saveRefreshToken(refreshToken)
    }
}