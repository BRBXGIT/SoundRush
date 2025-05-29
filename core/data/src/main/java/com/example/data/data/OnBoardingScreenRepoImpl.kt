package com.example.data.data

import com.example.data.domain.OnBoardingScreenRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.auth.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnBoardingScreenRepoImpl @Inject constructor(
    private val authManager: AuthManager
): OnBoardingScreenRepo {

    override val accessTokenFlow: Flow<String?>
        get() = authManager.accessTokenFlow

    override val authState = accessTokenFlow.map { token ->
        if (token.isNullOrBlank()) AuthState.LoggedOut else AuthState.LoggedIn
    }

    override suspend fun saveAccessToken(token: String) {
        authManager.accessTokenFlow
    }
}