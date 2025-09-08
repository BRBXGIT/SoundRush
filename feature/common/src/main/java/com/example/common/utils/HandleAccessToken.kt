package com.example.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HandleAccessToken(accessToken: String?, onHandle: (String) -> Unit) {
    LaunchedEffect(accessToken) {
        accessToken?.let {
            onHandle(it)
        }
    }
}