package com.example.data.domain

import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CommonRepo {

    val accessToken: Flow<String?>

    val refreshToken: Flow<String?>

    suspend fun refreshTokens(): NetworkResponse<TokensResponse>

    suspend fun saveTokens(accessToken: String, refreshToken: String)
}