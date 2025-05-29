package com.example.data.domain

import kotlinx.coroutines.flow.Flow

interface OnBoardingScreenRepo {

    val accessTokenFlow: Flow<String?>
}