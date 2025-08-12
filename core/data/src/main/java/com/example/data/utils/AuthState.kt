package com.example.data.utils

sealed interface AuthState {
    data object Loading: AuthState
    data object LoggedIn: AuthState
    data object LoggedOut: AuthState
}