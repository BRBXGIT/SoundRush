package com.example.home_screen.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.design_system.snackbars.sendRetrySnackbar
import com.example.design_system.snackbars.sendSimpleSnackbar
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
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val playlists = combine(
        _homeScreenState.map { it.accessToken }.filterNotNull().distinctUntilChanged(),
        _homeScreenState.map { it.refreshTrigger }.distinctUntilChanged()
    ) { token, trigger -> token to trigger }
        .flatMapLatest { (token, _) -> repo.getPlaylists(token) }
        .cachedIn(viewModelScope)

    // region: private helpers
    private fun updateState(transform: (HomeScreenState) -> HomeScreenState) {
        _homeScreenState.update(transform)
    }

    private fun refreshPlaylists() {
        updateState { it.copy(refreshTrigger = it.refreshTrigger + 1) }
    }

    private fun createPlaylist() {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isCreatePlaylistBSVisible = false) }

            val state = _homeScreenState.value
            val result = repo.createPlaylist(
                accessToken = state.accessToken,
                playlistName = state.playlistName,
                description = state.playlistDescription
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

    private fun deletePlaylists() {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isInDeleteMode = false) }

            val urns = _homeScreenState.value.playlistsUrnsForDelete
            urns.forEach { (urn, playlistName) ->
                val result = repo.deletePlaylist(_homeScreenState.value.accessToken, urn)

                if (result.error == NetworkErrors.SUCCESS) {
                    updateState { it.copy(playlistsUrnsForDelete = urns - urn) }
                } else {
                    sendSimpleSnackbar("Problem with deleting playlist: $playlistName")
                }
            }

            refreshPlaylists()
        }
    }

    // endregion
    fun sendIntent(intent: HomeScreenIntent) {
        when (intent) {
            // Auth & data
            is HomeScreenIntent.FetchAccessToken -> updateState { it.copy(accessToken = intent.token) }
            HomeScreenIntent.RefreshPlaylists -> refreshPlaylists()

            // Playlist creation
            HomeScreenIntent.ChangeCreatePlaylistBSVisibility ->
                updateState { it.copy(isCreatePlaylistBSVisible = !it.isCreatePlaylistBSVisible) }
            is HomeScreenIntent.ChangePlaylistName ->
                updateState { it.copy(playlistName = intent.name) }
            is HomeScreenIntent.ChangePlaylistDescription ->
                updateState { it.copy(playlistDescription = intent.description) }
            HomeScreenIntent.CreatePlaylist -> createPlaylist()

            // UX state
            is HomeScreenIntent.ChangeDidVibrate ->
                updateState { it.copy(didVibrate = intent.didVibrate) }

            // Playlist deletion
            HomeScreenIntent.ChangeIsInDeleteMode ->
                updateState { it.copy(isInDeleteMode = !it.isInDeleteMode) }
            is HomeScreenIntent.AddUrnToDeleteList ->
                updateState { it.copy(playlistsUrnsForDelete = it.playlistsUrnsForDelete + (intent.urn to intent.playlistName)) }
            is HomeScreenIntent.RemoveUrnFromList ->
                updateState { it.copy(playlistsUrnsForDelete = it.playlistsUrnsForDelete - intent.urn) }
            HomeScreenIntent.DeletePlaylists -> deletePlaylists()
        }
    }
}