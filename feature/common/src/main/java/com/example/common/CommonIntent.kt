package com.example.common

sealed class CommonIntent {
    data class RefreshUserTokens(
        val refreshToken: String,
        val onStart: () -> Unit,
        val onComplete: () -> Unit,
        val onError: () -> Unit
    ): CommonIntent()
    data class UpdateCommonState(val state: CommonState): CommonIntent()
}