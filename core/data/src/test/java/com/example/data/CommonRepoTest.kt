package com.example.data

import com.example.data.domain.CommonRepo
import com.example.data.repositories.CommonRepoImpl
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CommonRepoTest {

    private lateinit var repo: CommonRepo
    private val api = mockk<AuthApiInstance>()
    private val manager: AuthManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        repo = CommonRepoImpl(api, manager)
    }

    @Test
    fun `save tokens calls manager methods`() = runTest {
        val accessToken = ""
        val refreshToken = ""

        coEvery { repo.saveTokens(accessToken, refreshToken) } just Runs

        repo.saveTokens(accessToken, refreshToken)

        coVerify { manager.saveAccessToken(accessToken) }
        coVerify { manager.saveRefreshToken(refreshToken) }
    }
}