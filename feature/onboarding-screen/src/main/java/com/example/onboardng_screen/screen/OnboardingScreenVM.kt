package com.example.onboardng_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.OnboardingScreenRepo
import com.example.data.utils.AuthUtils
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.network.common.NetworkErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenVM @Inject constructor(
    private val repo: OnboardingScreenRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher,
): ViewModel() {

    private val _onboardingScreenState = MutableStateFlow(OnboardingScreenState())
    val onboardingScreenState = _onboardingScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        OnboardingScreenState()
    )

    private fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        viewModelScope.launch(dispatcherIo) {
            repo.saveTokens("${AuthUtils.TOKEN_TYPE} $accessToken", refreshToken)
            repo.saveOnboardingCompleted()
        }
    }

    private fun getTokens(
        code: String,
    ) {
        viewModelScope.launch(dispatcherIo) {
            _onboardingScreenState.update { state -> state.copy(isLoading = true) }

            val result = repo.getTokens(code)

            if (result.error == NetworkErrors.SUCCESS) {
                saveTokens(
                    accessToken = result.response!!.accessToken,
                    refreshToken = result.response!!.refreshToken
                )
            } else {
                sendRetrySnackbar(
                    label = result.label!!,
                    action = { getTokens(code) }
                )
            }

            _onboardingScreenState.update { state -> state.copy(isLoading = false) }
        }
    }

    fun sendIntent(intent: OnboardingScreenIntent) {
        when(intent) {
            is OnboardingScreenIntent.GetTokens -> getTokens(intent.code)
        }
    }
}