package com.example.common

import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.data.domain.CommonRepo
import com.example.data.utils.AuthUtils
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommonVMTest {

    private val dispatcher = StandardTestDispatcher()
    private val repo: CommonRepo = mockk(relaxed = true)

    private lateinit var vm: CommonVM

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        every { repo.accessToken } returns MutableStateFlow("")
        every { repo.refreshToken } returns MutableStateFlow("")

        vm = CommonVM(repo, dispatcher)

        runTest { advanceUntilIdle() }
    }

    @Test
    fun `refresh tokens calls repo methods on success`() = runTest {
        val accessToken = "acc"
        val refreshToken = "ref"

        coEvery { repo.refreshTokens() } returns NetworkResponse(
            response = TokensResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            ),
            error = NetworkErrors.SUCCESS,
            label = null
        )

        coEvery { repo.saveTokens(any(), any()) } just Runs

        vm.sendIntent(CommonIntent.RefreshTokens)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            repo.saveTokens("${AuthUtils.TOKEN_TYPE} $accessToken", refreshToken)
        }
    }
}