package com.example.data.utils

sealed interface OnboardingState {
    data object Loading: OnboardingState
    data object Completed: OnboardingState
    data object NotCompleted: OnboardingState
}