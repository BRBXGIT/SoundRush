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
import kotlinx.coroutines.flow.combine
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

    // TODO TEST PAGING!!!
    @OptIn(ExperimentalCoroutinesApi::class)
    val playlists = combine(
        _homeScreenState.map { it.accessToken }.filterNotNull().distinctUntilChanged(),
        _homeScreenState.map { it.refreshTrigger }.distinctUntilChanged()
    ) { token, trigger -> token to trigger }
        .flatMapLatest { (token, trigger) -> repo.getPlaylists(token) }
        .cachedIn(viewModelScope)

    private fun createPlaylist() {
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
                refreshPlaylists()
            } else {
                sendRetrySnackbar(
                    label = result.label!!,
                    action = { createPlaylist() }
                )
            }
        }
    }

    private fun refreshPlaylists() {
        _homeScreenState.value = _homeScreenState.value.copy(refreshTrigger = _homeScreenState.value.refreshTrigger + 1)
    }

    fun sendIntent(intent: HomeScreenIntent) {
        when(intent) {
            is HomeScreenIntent.FetchAccessToken -> {
                _homeScreenState.update { state ->
                    state.copy(accessToken = intent.token)
                }
            }
            HomeScreenIntent.RefreshPlaylists -> refreshPlaylists()

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
            HomeScreenIntent.CreatePlaylist -> createPlaylist()

            is HomeScreenIntent.ChangeDidVibrate -> {
                _homeScreenState.update { state ->
                    state.copy(didVibrate = intent.didVibrate)
                }
            }

            HomeScreenIntent.ChangeIsInDeleteMode -> {
                _homeScreenState.update { state ->
                    state.copy(isInDeleteMode = !_homeScreenState.value.isInDeleteMode)
                }
            }
            is HomeScreenIntent.AddUrnToDeleteList -> {
                _homeScreenState.update { state ->
                    state.copy(playlistsUrnsForDelete = _homeScreenState.value.playlistsUrnsForDelete + intent.urn)
                }
            }
            is HomeScreenIntent.RemoveUrnFromList -> {
                _homeScreenState.update { state ->
                    state.copy(playlistsUrnsForDelete = _homeScreenState.value.playlistsUrnsForDelete - intent.urn)
                }
            }
        }
    }
}