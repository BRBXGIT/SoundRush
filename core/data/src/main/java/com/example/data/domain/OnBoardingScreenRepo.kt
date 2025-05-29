package com.example.data.domain

import com.example.local.datastore.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface OnBoardingScreenRepo {

    val accessTokenFlow: Flow<String?>

    val authState: Flow<AuthState>

    suspend fun saveAccessToken(token: String)
}