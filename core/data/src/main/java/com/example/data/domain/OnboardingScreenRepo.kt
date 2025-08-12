package com.example.data.domain

interface OnboardingScreenRepo {

    suspend fun saveOnboardingCompleted()

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)
}