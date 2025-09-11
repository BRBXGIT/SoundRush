package com.example.common.state

sealed interface CommonIntent {

    // Auth & data
    data object RefreshTokens: CommonIntent

    // Ui state
    data class SetNavIndex(val index: Int): CommonIntent

    // Player state
    data class SetCurrentTrack(val track: Track): CommonIntent
    data class SetQueue(val tracks: List<Track>): CommonIntent
    data object ChangeIsPlaying: CommonIntent
}