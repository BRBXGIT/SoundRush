package com.example.data.domain

import com.example.data.utils.OnboardingState
import kotlinx.coroutines.flow.Flow

interface AppStartingRepo {

    val isOnboardingCompletedFlow: Flow<Boolean?>

    val onboardingState: Flow<OnboardingState>
}