package com.example.common.state

sealed interface CommonIntent {
    data object RefreshTokens: CommonIntent
}