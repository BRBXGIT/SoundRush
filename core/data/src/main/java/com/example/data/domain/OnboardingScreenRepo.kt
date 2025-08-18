package com.example.data.domain

import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkResponse

interface OnboardingScreenRepo {

    suspend fun saveOnboardingCompleted()

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun getTokens(code: String): NetworkResponse<TokensResponse>
}