package com.example.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.network.common.NetworkErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val repo: HomeScreenRepo,
    @Dispatcher(SoundRushDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val playlists = _homeScreenState
        .map { it.accessToken }
        .distinctUntilChanged()
        .filterNotNull()
        .flatMapLatest { token -> repo.getPlaylists(token) }
        .cachedIn(viewModelScope)

    private fun createPlaylist(
        onComplete: () -> Unit
    ) {
        viewModelScope.launch(dispatcherIo) {
            _homeScreenState.update { state ->
                state.copy(isCreatePlaylistBSVisible = false)
            }

            val result = repo.createPlaylist(
                accessToken = _homeScreenState.value.accessToken,
                playlistName = _homeScreenState.value.playlistName,
                description = _homeScreenState.value.playlistDescription
            )

            if (result.error == NetworkErrors.SUCCESS) {
                onComplete()
            } else {
                sendRetrySnackbar(
                    label = result.label!!,
                    action = { createPlaylist(onComplete) }
                )
            }
        }
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when(intent) {
            is HomeScreenIntent.FetchAccessToken -> _homeScreenState.update { state -> state.copy(accessToken = intent.token) }

            HomeScreenIntent.ChangeCreatePlaylistBSVisibility -> {
                _homeScreenState.update { state ->
                    state.copy(isCreatePlaylistBSVisible = !_homeScreenState.value.isCreatePlaylistBSVisible)
                }
            }
            is HomeScreenIntent.ChangePlaylistDescription -> {
                _homeScreenState.update { state ->
                    state.copy(playlistDescription = intent.description)
                }
            }
            is HomeScreenIntent.ChangePlaylistName -> {
                _homeScreenState.update { state ->
                    state.copy(playlistName = intent.name)
                }
            }
            is HomeScreenIntent.CreatePlaylist -> createPlaylist(intent.onComplete)
        }
    }
}