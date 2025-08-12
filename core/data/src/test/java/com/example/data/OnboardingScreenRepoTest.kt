package com.example.data

import app.cash.turbine.test
import com.example.data.repositories.OnboardingScreenRepoImpl
import com.example.data.utils.OnBoardingState
import com.example.local.datastore.onboarding.OnboardingManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingScreenRepoImplTest {

    private val manager: OnboardingManager = mockk()
    private lateinit var repo: OnboardingScreenRepoImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { manager.isOnboardingCompletedFlow } returns flowOf(null)
        repo = OnboardingScreenRepoImpl(manager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onboardingState emits NotCompleted when value is null`() = runTest {
        every { manager.isOnboardingCompletedFlow } returns flowOf(null)

        repo = OnboardingScreenRepoImpl(manager)

        repo.onboardingState.test {
            assertEquals(OnBoardingState.NotCompleted, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onboardingState emits NotCompleted when value is false`() = runTest {
        every { manager.isOnboardingCompletedFlow } returns flowOf(false)

        repo = OnboardingScreenRepoImpl(manager)

        repo.onboardingState.test {
            assertEquals(OnBoardingState.NotCompleted, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onboardingState emits Completed when value is true`() = runTest {
        every { manager.isOnboardingCompletedFlow } returns flowOf(true)

        repo = OnboardingScreenRepoImpl(manager)

        repo.onboardingState.test {
            assertEquals(OnBoardingState.Completed, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveOnboardingCompleted calls manager method`() = runTest {
        coEvery { manager.saveOnBoardingCompleted() } just Runs

        repo.saveOnboardingCompleted()

        coVerify { manager.saveOnBoardingCompleted() }
    }

    @Test
    fun `clearOnboardingCompleted calls manager method`() = runTest {
        coEvery { manager.clearIsOnBoardingCompleted() } just Runs

        repo.clearOnboardingCompleted()

        coVerify { manager.clearIsOnBoardingCompleted() }
    }
}