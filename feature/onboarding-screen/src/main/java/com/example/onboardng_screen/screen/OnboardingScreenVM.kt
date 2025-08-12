package com.example.onboardng_screen.screen

import androidx.lifecycle.ViewModel
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.OnboardingScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenVM @Inject constructor(
    private val repo: OnboardingScreenRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    suspend fun saveAccessToken(token: String) = repo.saveAccessToken(token)

    suspend fun saveRefreshToken(token: String) = repo.saveRefreshToken(token)
}