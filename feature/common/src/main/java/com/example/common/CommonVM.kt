package com.example.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.utils.AuthUtils
import com.example.data.domain.AuthRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonVM @Inject constructor(
    private val authRepository: AuthRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _commonState = MutableStateFlow(CommonState())
    val commonState = _commonState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CommonState()
    )

    private fun updateCommonState(state: CommonState) {
        _commonState.value = state
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch(dispatcherIo) {
            authRepository.saveUserTokens(accessToken, refreshToken)
        }
    }

    private fun refreshUserTokens(
        refreshToken: String,
        onComplete: () -> Unit,
    ) {
        viewModelScope.launch(dispatcherIo) {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = CommonUtils.REFRESHING_TOKENS_TEXT
                )
            )
            _commonState.value = _commonState.value.copy(
                isUserTokensRefreshing = true
            )

            val response = authRepository.refreshUserTokens(
                clientId = AuthUtils.CLIENT_ID,
                clientSecret = AuthUtils.CLIENT_SECRET,
                refreshToken = refreshToken
            )

            val networkError = processNetworkErrors(response.code())
            if (networkError == NetworkErrors.SUCCESS) {
                saveTokens(response.body()!!.accessToken, response.body()!!.refreshToken)
                onComplete()
                _commonState.value = _commonState.value.copy(
                    isUserTokensRefreshing = false
                )
            } else {
                _commonState.value = _commonState.value.copy(
                    isUserTokensRefreshing = false
                )
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = CommonUtils.REFRESHING_TOKENS_ERROR_TEXT,
                        action = SnackbarAction(
                            name = CommonUtils.RETRY_TEXT,
                            action = { refreshUserTokens(refreshToken, onComplete) }
                        )
                    )
                )
            }
        }
    }

    fun sendIntent(intent: CommonIntent) {
        when (intent) {
            is CommonIntent.RefreshUserTokens -> refreshUserTokens(
                refreshToken = intent.refreshToken,
                onComplete = intent.onComplete
            )
            is CommonIntent.UpdateCommonState -> updateCommonState(intent.state)
        }
    }
}