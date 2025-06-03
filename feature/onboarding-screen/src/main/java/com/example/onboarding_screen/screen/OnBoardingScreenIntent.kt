package com.example.onboarding_screen.screen

sealed class OnBoardingScreenIntent {
    data class FetchUserTokens(
        val grantType: String = "authorization_code",
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String,
        val codeVerifier: String,
        val code: String
    ): OnBoardingScreenIntent()
}