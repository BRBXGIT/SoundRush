package com.example.data

import com.example.data.repositories.OnboardingScreenRepoImpl
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.onboarding.OnboardingManager
import com.example.network.auth.api.AuthApiInstance
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
    private val authApiInstance: AuthApiInstance = mockk()
    private lateinit var repo: OnboardingScreenRepoImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        every { onboardingManager.isOnboardingCompletedFlow } returns flowOf(null)
        repo = OnboardingScreenRepoImpl(onboardingManager, authManager, authApiInstance)
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
    fun `saveTokens calls manager method`() = runTest {
        val accessToken = ""
        val refreshToken = ""
        coEvery { authManager.saveAccessToken(accessToken) } just Runs
        coEvery { authManager.saveRefreshToken(refreshToken) } just Runs

        repo.saveTokens(accessToken, refreshToken)

        coVerify { authManager.saveAccessToken(accessToken) }
        coVerify { authManager.saveRefreshToken(refreshToken) }
    }
}