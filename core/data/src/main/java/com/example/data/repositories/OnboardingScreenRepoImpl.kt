package com.example.data.repositories

import com.example.data.domain.OnboardingScreenRepo
import com.example.data.utils.AuthUtils
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

    override suspend fun getTokens(code: String): NetworkResponse<TokensResponse> = NetworkRequest.exec {
        authApiInstance.getTokens(
            clientId = AuthUtils.CLIENT_ID,
            clientSecret = AuthUtils.CLIENT_SECRET,
            redirectUri = AuthUtils.REDIRECT_URI,
            codeVerifier = AuthUtils.CODE_VERIFIER,
            code = code
        )
    }
}