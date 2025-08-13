package com.example.onboardng_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.OnboardingScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenVM @Inject constructor(
    private val repo: OnboardingScreenRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        viewModelScope.launch(dispatcherIo) {
            repo.saveAccessToken(accessToken)
            repo.saveRefreshToken(refreshToken)
        }
    }
}