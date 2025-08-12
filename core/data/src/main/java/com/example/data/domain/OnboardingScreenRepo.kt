package com.example.data.domain

import com.example.data.utils.OnBoardingState
import kotlinx.coroutines.flow.Flow

interface OnboardingScreenRepo {

    val isOnboardingCompletedFlow: Flow<Boolean?>

    val onboardingState: Flow<OnBoardingState>

    suspend fun saveOnboardingCompleted()

    suspend fun clearOnboardingCompleted()
}