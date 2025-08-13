package com.example.data.repositories

import com.example.data.domain.OnboardingScreenRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.onboarding.OnboardingManager
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkRequest
import com.example.network.common.NetworkResponse
import javax.inject.Inject

class OnboardingScreenRepoImpl @Inject constructor(
    private val onboardingManager: OnboardingManager,
    private val authManager: AuthManager,
    private val authApiInstance: AuthApiInstance
): OnboardingScreenRepo {

    override suspend fun saveOnboardingCompleted() = onboardingManager.saveOnBoardingCompleted()

    override suspend fun saveAccessToken(token: String) = authManager.saveAccessToken(token)

    override suspend fun saveRefreshToken(token: String) = authManager.saveRefreshToken(token)

    override suspend fun getTokens(
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        codeVerifier: String,
        code: String
    ): NetworkResponse<TokensResponse> = NetworkRequest.exec {
        authApiInstance.getTokens(
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = redirectUri,
            codeVerifier = codeVerifier,
            code = code
        )
    }
}