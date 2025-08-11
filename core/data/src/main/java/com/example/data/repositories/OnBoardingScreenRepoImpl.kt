package com.example.data.repositories

import com.example.data.domain.OnBoardingScreenRepo
import com.example.data.utils.OnBoardingState
import com.example.local.datastore.onboarding.OnBoardingManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OnBoardingScreenRepoImpl @Inject constructor(
    private val manager: OnBoardingManager
): OnBoardingScreenRepo {

    override val isOnBoardingCompletedFlow = manager.isOnboardingCompletedFlow

    override val onBoardingState = isOnBoardingCompletedFlow.map { isCompleted ->
        if ((isCompleted == null) or (isCompleted == false)) OnBoardingState.NotCompleted else OnBoardingState.Completed
    }

    override suspend fun saveOnboardingCompleted() = manager.saveOnBoardingCompleted()

    override suspend fun clearOnboardingCompleted() = manager.clearIsOnBoardingCompleted()
}