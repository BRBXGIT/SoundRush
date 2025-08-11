package com.example.data.utils

sealed interface OnBoardingState {
    data object Loading: OnBoardingState
    data object Completed: OnBoardingState
    data object NotCompleted: OnBoardingState
}