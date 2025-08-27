package com.example.onboardng_screen

import com.example.data.domain.OnboardingScreenRepo
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
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
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
    fun `test loading state and save tokens functions when get tokens is success`() = runTest {
        val code = "123"

        coEvery {
            repo.getTokens(code)
        } returns successResult()

        coEvery { repo.saveTokens("acc_123", "ref_123") } just Runs

        assertFalse(vm.onboardingScreenState.value.isLoading)

        vm.sendIntent(
            OnboardingScreenIntent.GetTokens(
                code = code,
            )
        )

        advanceUntilIdle()

        coVerify(exactly = 1) { repo.saveTokens("acc_123", "ref_123") }

        assertFalse(vm.onboardingScreenState.value.isLoading)
    }

    @Test
    fun `on error do not call and loading state is false`() = runTest {
        val code = "123"
        coEvery { repo.getTokens(code) } returns errorResult()

        vm.sendIntent(
            OnboardingScreenIntent.GetTokens(
                code = code,
            )
        )

        advanceUntilIdle()

        assertFalse(vm.onboardingScreenState.value.isLoading)
    }
}