package com.example.onboarding_screen.screen

data class OnBoardingScreenState(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val isUserTokensLoading: Boolean = false
)
