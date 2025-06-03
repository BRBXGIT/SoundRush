package com.example.onboarding_screen.screen

sealed class OnBoardingScreenIntent {
    data class SaveAccessToken(val accessToken: String): OnBoardingScreenIntent()

    data class FetchUserTokens(
        val grantType: String,
        val clientId: String,
        val redirectUri: String,
        val codeVerifier: String,
        val code: String
    ): OnBoardingScreenIntent()

    data class UpdateScreenState(val state: OnBoardingScreenState): OnBoardingScreenIntent()
}