package com.example.onboardng_screen.screen

sealed interface OnboardingScreenIntent {
    data class GetTokens(val code: String): OnboardingScreenIntent
}