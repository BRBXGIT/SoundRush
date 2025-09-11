package com.example.common.state

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.CommonRepo
import com.example.data.utils.AuthUtils
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.network.common.NetworkErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonVM @Inject constructor(
    private val player: ExoPlayer,
    private val repo: CommonRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    init {
        observeTokens()
    }

    private val _commonState = MutableStateFlow(CommonState())
    val commonState = _commonState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CommonState()
    )

    // Player region
    @OptIn(UnstableApi::class)
    private fun setUpPlayer() {
        val queue = _commonState.value.tracksQueue
        if (queue.isEmpty()) return

        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setDefaultRequestProperties(
                mapOf("Authorization" to _commonState.value.accessToken!!)
            )

        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)

        val mediaSources = queue.map { track ->
            mediaSourceFactory.createMediaSource(
                MediaItem.fromUri(track.link)
            )
        }

        val concatenated = ConcatenatingMediaSource(*mediaSources.toTypedArray())

        player.setMediaSource(concatenated)
        player.prepare()
        player.playWhenReady = true
        updateState { it.copy(currentTrack = it.currentTrack.copy(isPlaying = true)) }
    }

    private fun playPause() {
        updateState { it.copy(currentTrack = it.currentTrack.copy(isPlaying = !it.currentTrack.isPlaying)) }

        if (_commonState.value.currentTrack.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    // Auth & data region
    private fun observeTokens() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                repo.accessToken,
                repo.refreshToken
            ) { accessToken, refreshToken ->
                accessToken to refreshToken
            }.collect { (accessToken, refreshToken) ->
                _commonState.update { state ->
                    state.copy(
                        refreshToken = refreshToken,
                        accessToken = accessToken
                    )
                }
            }
        }
    }

    private fun updateTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch(dispatcherIo) {
            repo.saveTokens("${AuthUtils.TOKEN_TYPE} $accessToken", refreshToken)
        }
    }

    private fun refreshTokens() {
        viewModelScope.launch(dispatcherIo) {
            val result = repo.refreshTokens()

            if (result.error == NetworkErrors.SUCCESS) {
                val response = result.response!!
                updateTokens(
                    refreshToken = response.refreshToken,
                    accessToken = response.accessToken
                )
            } else {
                sendRetrySnackbar(
                    label = result.label!!,
                    action = { refreshTokens() }
                )
            }
        }
    }

    // Private helpers region
    private fun updateState(transform: (CommonState) -> CommonState) {
        _commonState.update(transform)
    }

    // End region
    fun sendIntent(intent: CommonIntent) {
        when(intent) {
            // Auth & data
            CommonIntent.RefreshTokens -> refreshTokens()

            // Ui state
            is CommonIntent.SetNavIndex -> updateState { it.copy(currentNavIndex = intent.index) }

            // Player state
            is CommonIntent.SetCurrentTrack ->
                updateState { it.copy(currentTrack = intent.track) }
            CommonIntent.ChangeIsPlaying -> playPause()
            is CommonIntent.SetQueue -> {
                updateState { it.copy(tracksQueue = intent.tracks) }
                setUpPlayer()
            }
        }
    }
}