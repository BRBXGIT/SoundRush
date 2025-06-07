package com.example.onboarding_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.data.domain.AuthRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
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
    private fun saveTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch(dispatcherIo) {
            authRepo.saveUserTokens(accessToken, refreshToken)
        }
    }

    private val _onBoardingScreenState = MutableStateFlow(OnBoardingScreenState())
    val onBoardingScreenState = _onBoardingScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        OnBoardingScreenState()
    )

    private fun fetchUserTokens(
        grantType: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        codeVerifier: String,
        code: String
    ) {
        viewModelScope.launch(dispatcherIo) {
            _onBoardingScreenState.value.copy(isUserTokensLoading = true)

            val response = authRepo.getUserTokens(
                grantType,
                clientId,
                clientSecret,
                redirectUri,
                codeVerifier,
                code
            )
            val networkError = processNetworkErrors(response.code())
            if(networkError == NetworkErrors.SUCCESS) {
                saveTokens(
                    accessToken = response.body()!!.accessToken,
                    refreshToken = response.body()!!.refreshToken
                )
            } else {
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = processNetworkErrorsForUi(networkError),
                        action = SnackbarAction(
                            name = "Refresh",
                            action = {
                                fetchUserTokens(
                                    grantType,
                                    clientId,
                                    clientSecret,
                                    redirectUri,
                                    codeVerifier,
                                    code
                                )
                            }
                        )
                    )
                )
            }

            _onBoardingScreenState.value.copy(isUserTokensLoading = false)
        }
    }

    fun sendIntent(intent: OnBoardingScreenIntent) {
        when(intent) {
            is OnBoardingScreenIntent.FetchUserTokens -> {
                fetchUserTokens(
                    intent.grantType,
                    intent.clientId,
                    intent.clientSecret,
                    intent.redirectUri,
                    intent.codeVerifier,
                    intent.code
                )
            }
        }
    }
}