package com.example.common.state

data class CommonState(
    // Auth & data
    val refreshToken: String? = null,
    val accessToken: String? = null,

    // Ui state
    val isLoading: Boolean = false,

    val currentNavIndex: Int = 0,

    // Current track
    val isPlaying: Boolean = false,
    val posterPath: String? = null,
    val name: String = "Nothing is playing",
    val author: String = "Unknown",

    // Tracks queue
    val tracksQueue: List<String> = emptyList()
)
