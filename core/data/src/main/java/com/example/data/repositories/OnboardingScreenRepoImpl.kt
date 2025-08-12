package com.example.data.repositories

import com.example.data.domain.OnboardingScreenRepo
import com.example.data.utils.OnBoardingState
import com.example.local.datastore.onboarding.OnboardingManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnboardingScreenRepoImpl @Inject constructor(
    private val manager: OnboardingManager
): OnboardingScreenRepo {

    override val isOnboardingCompletedFlow = manager.isOnboardingCompletedFlow

    override val onboardingState = isOnboardingCompletedFlow.map { isCompleted ->
        if ((isCompleted == null) or (isCompleted == false)) OnBoardingState.NotCompleted else OnBoardingState.Completed
    }

    override suspend fun saveOnboardingCompleted() = manager.saveOnBoardingCompleted()

    override suspend fun clearOnboardingCompleted() = manager.clearIsOnBoardingCompleted()
}