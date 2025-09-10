package com.example.common

import app.cash.turbine.test
import com.example.common.state.CommonIntent
import com.example.common.state.CommonState
import com.example.common.state.CommonVM
import com.example.data.domain.CommonRepo
import com.example.data.utils.AuthUtils
import com.example.network.auth.models.TokensResponse
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkResponse
import io.mockk.Runs
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
        vm = CommonVM(repo, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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

    @Test
    fun `state changes correctly`() = runTest {
        vm.commonState.test {
            val initial = awaitItem()
            assertEquals(CommonState(), initial)

            vm.sendIntent(CommonIntent.SetNavIndex(1))
            val posterPath = null
            val name = "name"
            val author = "author"
            vm.sendIntent(CommonIntent.SetCurrentTrack(posterPath, name, author))
            vm.sendIntent(CommonIntent.ChangeIsPlaying)

            val after = awaitItem()
            assertNull(after.posterPath)
            assertEquals(name, after.name)
            assertEquals(author, after.author)
            assertTrue(after.isPlaying)
        }
    }
}