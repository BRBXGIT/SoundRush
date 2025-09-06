package com.example.common.state

sealed interface CommonIntent {
    data object RefreshTokens: CommonIntent
    data class SetNavIndex(val index: Int): CommonIntent
}