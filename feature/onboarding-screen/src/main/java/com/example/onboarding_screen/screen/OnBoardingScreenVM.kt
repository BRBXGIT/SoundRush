package com.example.onboarding_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingScreenVM @Inject constructor(
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
    private val authRepo: AuthRepo
): ViewModel() {
    private fun saveAccessToken(token: String) {
        viewModelScope.launch(dispatcherIo) {
            authRepo.saveAccessToken(token)
        }
    }

    fun sendIntent(intent: OnBoardingScreenIntent) {
        when(intent) {
            is OnBoardingScreenIntent.SaveAccessToken -> saveAccessToken(intent.accessToken)
        }
    }
}