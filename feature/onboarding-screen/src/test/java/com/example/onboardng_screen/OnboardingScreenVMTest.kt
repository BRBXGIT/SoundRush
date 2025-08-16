package com.example.onboardng_screen

import com.example.data.domain.OnboardingScreenRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkResponse
import com.example.onboardng_screen.screen.OnboardingScreenIntent
import com.example.onboardng_screen.screen.OnboardingScreenState
import com.example.onboardng_screen.screen.OnboardingScreenVM
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingScreenVMTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repo: OnboardingScreenRepo
    private lateinit var vm: OnboardingScreenVM

    @Before
    fun setUp() {
        repo = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
        vm = OnboardingScreenVM(
            repo = repo,
            dispatcherIo = dispatcher,
            dispatcherMain = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun successTokens() = TokensResponse(
        accessToken = "acc_123",
        refreshToken = "ref_456"
    )

    private fun successResult(): NetworkResponse<TokensResponse> =
        NetworkResponse(
            error = NetworkErrors.SUCCESS,
            label = null,
            response = successTokens()
        )

    private fun errorResult(): NetworkResponse<TokensResponse> =
        NetworkResponse(
            error = NetworkErrors.UNAUTHORIZED,
            label = "Seems you are unauthorized",
            response = null
        )

    @Test
    fun `state is default at start`() {
        assertEquals(OnboardingScreenState(), vm.onboardingScreenState.value)
    }

    @Test
    fun `test loading state, onComplete and save tokens functions when get tokens is succcess`() = runTest {
        val code = "123"

        coEvery {
            repo.getTokens(any(), any(), any(), any(), code)
        } returns successResult()

        coEvery { repo.saveAccessToken(any()) } just Runs
        coEvery { repo.saveRefreshToken(any()) } just Runs

        assertFalse(vm.onboardingScreenState.value.isLoading)

        var isCompleted = false
        vm.sendIntent(
            OnboardingScreenIntent.GetTokens(
                code = code,
                onComplete = { isCompleted = true }
            )
        )

        advanceUntilIdle()

        coVerify(exactly = 1) { repo.saveAccessToken("acc_123") }
        coVerify(exactly = 1) { repo.saveRefreshToken("ref_456") }

        assertFalse(vm.onboardingScreenState.value.isLoading)
        assertTrue(isCompleted)
    }

    @Test
    fun `on error do not call onComplete and loading state is false`() = runTest {
        val code = "123"
        coEvery { repo.getTokens(any(), any(), any(), any(), code) } returns errorResult()

        var completed = false
        vm.sendIntent(
            OnboardingScreenIntent.GetTokens(
                code = code,
                onComplete = { completed = true }
            )
        )

        advanceUntilIdle()

        assertFalse(completed)
        assertFalse(vm.onboardingScreenState.value.isLoading)
    }
}