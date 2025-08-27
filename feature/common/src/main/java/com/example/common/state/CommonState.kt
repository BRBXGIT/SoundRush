package com.example.common.state

data class CommonState(
    val refreshToken: String? = null,
    val accessToken: String? = null,

    val isLoading: Boolean = false,

    val currentNavIndex: Int = 0
)
