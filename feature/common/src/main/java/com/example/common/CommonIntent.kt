package com.example.common

sealed class CommonIntent {
    data class RefreshUserTokens(
        val refreshToken: String,
        val onComplete: () -> Unit,
    ): CommonIntent()
    data class UpdateCommonState(val state: CommonState): CommonIntent()
}