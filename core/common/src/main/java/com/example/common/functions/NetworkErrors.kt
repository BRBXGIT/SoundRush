package com.example.common.functions

enum class NetworkErrors: NetworkError {
    SUCCESS,
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    UNKNOWN
}