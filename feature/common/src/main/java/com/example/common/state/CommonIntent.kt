package com.example.common.state

sealed interface CommonIntent {

    // Auth & data
    data object RefreshTokens: CommonIntent

    // Ui state
    data class SetNavIndex(val index: Int): CommonIntent

    data class SetCurrentTrack(
        val posterPath: String?,
        val name: String,
        val author: String
    ): CommonIntent
    data object ChangeIsPlaying: CommonIntent
}