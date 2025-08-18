package com.example.data.repositories

import com.example.data.domain.AppStartingRepo
import com.example.data.utils.OnboardingState
import com.example.local.datastore.onboarding.OnboardingManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppStartingRepoImpl @Inject constructor(
    onboardingManager: OnboardingManager
): AppStartingRepo {

    override val isOnboardingCompletedFlow = onboardingManager.isOnboardingCompletedFlow

    override val onboardingState = isOnboardingCompletedFlow.map { isCompleted ->
        if ((isCompleted == null) or (isCompleted == false)) OnboardingState.NotCompleted else OnboardingState.Completed
    }
}