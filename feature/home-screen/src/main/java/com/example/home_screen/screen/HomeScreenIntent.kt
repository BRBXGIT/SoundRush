package com.example.home_screen.screen

sealed interface HomeScreenIntent {
    data class FetchAccessToken(val token: String): HomeScreenIntent
}