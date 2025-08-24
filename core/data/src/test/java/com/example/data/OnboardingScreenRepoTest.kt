package com.example.data

import com.example.data.repositories.OnboardingScreenRepoImpl
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.onboarding.OnboardingManager
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkErrors
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

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

    @Test
    fun `get tokens returns success if code is 200`() = runTest {
        val body = TokensResponse()
        val response = Response.success(body)
        coEvery {
            authApiInstance.getTokens(
                clientId = "",
                clientSecret = "",
                redirectUri = "",
                codeVerifier = "",
                code = ""
            )
        } returns response

        val result = repo.getTokens(
            code = ""
        )

        assertEquals(NetworkErrors.SUCCESS, result.error)
        assertEquals(body, result.response)
    }

    @Test
    fun `get tokens returns internet exception if there is no connection`() = runTest {
        coEvery {
            authApiInstance.getTokens(
                clientId = "",
                clientSecret = "",
                redirectUri = "",
                codeVerifier = "",
                code = ""
            )
        } throws UnknownHostException()

        val result = repo.getTokens(
            code = ""
        )

        assertNull(result.response)
        assertEquals(NetworkErrors.INTERNET, result.error)
    }

    @Test
    fun `get tokens returns NetworkError if code is not 200`() = runTest {
        val errorCode = 401
        val networkError = NetworkErrors.UNAUTHORIZED

        val body = ResponseBody.create(MediaType.get("application/json"), "")
        val response = Response.error<TokensResponse>(errorCode, body)

        coEvery { authApiInstance.getTokens(
            clientId = "",
            clientSecret = "",
            redirectUri = "",
            codeVerifier = "",
            code = ""
        ) } returns response

        val result = repo.getTokens(
            code = ""
        )

        assertEquals(networkError, result.error)
    }
}