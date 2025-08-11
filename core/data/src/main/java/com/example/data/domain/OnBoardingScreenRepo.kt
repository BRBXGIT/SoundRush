package com.example.data.domain

import com.example.data.utils.OnBoardingState
import kotlinx.coroutines.flow.Flow

interface OnBoardingScreenRepo {

    val isOnBoardingCompletedFlow: Flow<Boolean?>

    val onBoardingState: Flow<OnBoardingState>

    suspend fun saveOnboardingCompleted()

    suspend fun clearOnboardingCompleted()
}