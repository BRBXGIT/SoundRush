package com.example.data

import com.example.data.repositories.OnboardingScreenRepoImpl
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.onboarding.OnboardingManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
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
class OnboardingScreenRepoTest {

    private val onboardingManager: OnboardingManager = mockk()
    private val authManager: AuthManager = mockk()
    private lateinit var repo: OnboardingScreenRepoImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { onboardingManager.isOnboardingCompletedFlow } returns flowOf(null)
        repo = OnboardingScreenRepoImpl(onboardingManager, authManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveOnboardingCompleted calls manager method`() = runTest {
        coEvery { onboardingManager.saveOnBoardingCompleted() } just Runs

        repo.saveOnboardingCompleted()

        coVerify { onboardingManager.saveOnBoardingCompleted() }
    }

    @Test
    fun `saveAccessToken calls manager method`() = runTest {
        val token = "OAuth token"
        coEvery { authManager.saveAccessToken(token) } just Runs

        repo.saveAccessToken(token)

        coVerify { authManager.saveAccessToken(token) }
    }

    @Test
    fun `saveRefreshToken calls manager method`() = runTest {
        val token = "token"
        coEvery { authManager.saveRefreshToken(token) } just Runs

        repo.saveRefreshToken(token)

        coVerify { authManager.saveRefreshToken(token) }
    }
}