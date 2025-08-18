package com.example.soundrush

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.domain.AppStartingRepo
import com.example.data.utils.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AppStartingVM @Inject constructor(
    repo: AppStartingRepo
): ViewModel() {

    val onboardingState = repo.onboardingState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        OnboardingState.Loading
    )
}