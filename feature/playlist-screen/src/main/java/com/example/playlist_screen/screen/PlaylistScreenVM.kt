package com.example.playlist_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.CommonUtils
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.common.functions.NetworkErrors
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.data.domain.AuthRepo
import com.example.data.domain.PlaylistScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenVM @Inject constructor(
    private val repository: PlaylistScreenRepo,
    private val authRepository: AuthRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _playlistScreenState = MutableStateFlow(PlaylistScreenState())
    val playlistScreenState = _playlistScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PlaylistScreenState()
    )

    private fun updateScreenState(state: PlaylistScreenState) {
        _playlistScreenState.value = state
    }

    private fun fetchTokens() {
        viewModelScope.launch(dispatcherIo) {
            _playlistScreenState.value = _playlistScreenState.value.copy(
                accessToken = authRepository.accessTokenFlow.first(),
                refreshToken = authRepository.refreshTokenFlow.first()
            )
        }
    }

    private fun fetchPlaylist(
        playlistId: Int,
        onUnauthorized: () -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            _playlistScreenState.update { state ->
                state.copy(isLoading = true)
            }

            val response = repository.getPlaylist(
                oAuthToken = "${CommonUtils.TOKEN_TYPE} ${_playlistScreenState.value.accessToken}",
                playlistId = playlistId
            )

            val networkError = processNetworkErrors(response.code())
            when (networkError) {
                NetworkErrors.SUCCESS -> {
                    _playlistScreenState.update { state ->
                        state.copy(
                            isLoading = false,
                            playlist = response.body()
                        )
                    }
                }
                NetworkErrors.UNAUTHORIZED -> {
                    _playlistScreenState.update { state ->
                        state.copy(isLoading = false)
                    }
                    onUnauthorized()
                }
                else -> {
                    _playlistScreenState.update { state ->
                        state.copy(isLoading = false)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = processNetworkErrorsForUi(networkError),
                            action = SnackbarAction(
                                name = CommonUtils.RETRY_TEXT,
                                action = { fetchPlaylist(playlistId, onUnauthorized) }
                            )
                        )
                    )
                }
            }
        }
    }

    fun sendIntent(intent: PlaylistScreenIntent) {
        when (intent) {
            is PlaylistScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
            is PlaylistScreenIntent.FetchPlaylist -> fetchPlaylist(
                playlistId = _playlistScreenState.value.playlistId,
                onUnauthorized = intent.onUnauthorized
            )
            is PlaylistScreenIntent.FetchTokens -> fetchTokens()
        }
    }

    init {
        fetchTokens()
    }
}