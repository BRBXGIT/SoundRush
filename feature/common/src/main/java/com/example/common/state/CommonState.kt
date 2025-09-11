package com.example.common.state

data class CommonState(
    // Auth & data
    val refreshToken: String? = null,
    val accessToken: String? = null,

    // Ui state
    val isLoading: Boolean = false,
    val currentNavIndex: Int = 0,
    val currentTrack: Track = Track(),

    // Tracks queue
    val tracksQueue: List<Track> = emptyList()
)
