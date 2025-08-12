package com.example.data.repositories

import com.example.data.domain.OnboardingScreenRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.onboarding.OnboardingManager
import javax.inject.Inject

class OnboardingScreenRepoImpl @Inject constructor(
    private val onboardingManager: OnboardingManager,
    private val authManager: AuthManager
): OnboardingScreenRepo {

    override suspend fun saveOnboardingCompleted() = onboardingManager.saveOnBoardingCompleted()

    override suspend fun saveAccessToken(token: String) = authManager.saveAccessToken(token)

    override suspend fun saveRefreshToken(token: String) = authManager.saveRefreshToken(token)
}