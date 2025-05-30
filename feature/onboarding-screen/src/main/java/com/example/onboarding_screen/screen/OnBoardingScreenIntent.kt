package com.example.onboarding_screen.screen

sealed class OnBoardingScreenIntent {
    data class SaveAccessToken(val accessToken: String): OnBoardingScreenIntent()
}