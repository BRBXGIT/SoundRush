package com.example.network.common

data class NetworkException(
    val error: NetworkError,
    val label: String
): Exception()
