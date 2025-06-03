package com.example.onboarding_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    private val _onBoardingScreenState = MutableStateFlow(OnBoardingScreenState())
    val onBoardingScreenState = _onBoardingScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        OnBoardingScreenState()
    )

    private fun updateScreenSate(state: OnBoardingScreenState) {
        _onBoardingScreenState.value = state
    }

    private fun fetchUserTokens(
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        code: String
    ) {
        viewModelScope.launch(dispatcherIo) {
            _onBoardingScreenState.value.copy(isUserTokensLoading = true)

            authRepo.getUserTokens(
                grantType,
                clientId,
                redirectUri,
                codeVerifier,
                code
            )

            _onBoardingScreenState.value.copy(isUserTokensLoading = false)
        }
    }

    fun sendIntent(intent: OnBoardingScreenIntent) {
        when(intent) {
            is OnBoardingScreenIntent.SaveAccessToken -> saveAccessToken(intent.accessToken)
            is OnBoardingScreenIntent.UpdateScreenState -> updateScreenSate(intent.state)
            is OnBoardingScreenIntent.FetchUserTokens -> {
                fetchUserTokens(
                    intent.grantType,
                    intent.clientId,
                    intent.redirectUri,
                    intent.codeVerifier,
                    intent.code
                )
            }
        }
    }
}