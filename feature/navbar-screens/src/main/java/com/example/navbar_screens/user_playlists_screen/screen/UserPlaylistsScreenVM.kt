package com.example.navbar_screens.user_playlists_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.utils.AuthUtils
import com.example.data.domain.AuthRepo
import com.example.data.domain.UserPlaylistsScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class UserPlaylistsScreenVM @Inject constructor(
    private val repository: UserPlaylistsScreenRepo,
    private val authRepository: AuthRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _userPlaylistsScreenState = MutableStateFlow(UserPlaylistsScreenState())
    val userPlaylistsScreenState = _userPlaylistsScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        UserPlaylistsScreenState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val playlists = _userPlaylistsScreenState
        .map { it.accessToken }
        .filterNotNull()
        .flatMapLatest { token ->
            repository.getUserPlaylists("${UserPlaylistsScreenUtils.TOKEN_TYPE} $token")
        }
        .cachedIn(viewModelScope)


    private fun fetchTokens() {
        viewModelScope.launch(dispatcherIo) {
            _userPlaylistsScreenState.value = _userPlaylistsScreenState.value.copy(
                accessToken = authRepository.accessTokenFlow.first(),
                refreshToken = authRepository.refreshTokenFlow.first()
            )
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch(dispatcherIo) {
            authRepository.saveUserTokens(accessToken, refreshToken)
        }
    }

    private fun refreshUserTokens(
        onComplete: () -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            _userPlaylistsScreenState.value = _userPlaylistsScreenState.value.copy(
                isTokensRefreshing = true
            )

            val response = authRepository.refreshUserTokens(
                clientId = AuthUtils.CLIENT_ID,
                clientSecret = AuthUtils.CLIENT_SECRET,
                refreshToken = _userPlaylistsScreenState.value.refreshToken!!
            )
            val networkError = processNetworkErrors(response.code())
            if (networkError == NetworkErrors.SUCCESS) {
                saveTokens(response.body()!!.accessToken, response.body()!!.refreshToken)
                fetchTokens()
                onComplete()
                _userPlaylistsScreenState.value = _userPlaylistsScreenState.value.copy(
                    isTokensRefreshing = false
                )
            } else {
                _userPlaylistsScreenState.value = _userPlaylistsScreenState.value.copy(
                    isTokensRefreshing = false
                )
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = UserPlaylistsScreenUtils.REFRESHING_TOKENS_ERROR_TEXT,
                        action = SnackbarAction(
                            name = UserPlaylistsScreenUtils.RETRY_TEXT,
                            action = { refreshUserTokens(onComplete) }
                        )
                    )
                )
            }
        }
    }

    fun sendIntent(intent: UserPlaylistsScreenIntent) {
        when (intent) {
            is UserPlaylistsScreenIntent.RefreshUserTokens -> refreshUserTokens(intent.onComplete)
        }
    }

    init {
        fetchTokens()
    }
}