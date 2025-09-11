package com.example.common.state

data class Track(
    val link: String = "",
    val isPlaying: Boolean = false,
    val posterPath: String? = null,
    val name: String = "Nothing is playing",
    val author: String = "Unknown",
)
