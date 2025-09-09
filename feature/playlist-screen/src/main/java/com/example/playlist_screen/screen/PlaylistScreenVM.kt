package com.example.playlist_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.domain.PlaylistScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenVM @Inject constructor(
    private val repo: PlaylistScreenRepo
) : ViewModel() {

    private val _playlistScreenState = MutableStateFlow(PlaylistScreenState())
    val playlistScreenState = _playlistScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        PlaylistScreenState()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tracks = combine(
        _playlistScreenState.map { it.accessToken }.filterNotNull().distinctUntilChanged(),
        _playlistScreenState.map { it.refreshTrigger }.distinctUntilChanged(),
        _playlistScreenState.map { it.playlistUrn }.distinctUntilChanged()
    ) { token, trigger, playlistUrn ->
        TracksPagingParameters(
            accessToken = token,
            playlistUrn = playlistUrn,
            refreshTrigger = trigger
        )
    }
        .flatMapLatest { (token, playlistUrn, _) ->
            repo.getPlaylistTracks(token, playlistUrn)
        }
        .cachedIn(viewModelScope)

    // region: private helpers
    private fun updateState(transform: (PlaylistScreenState) -> PlaylistScreenState) {
        _playlistScreenState.update(transform)
    }

    private fun refreshTracks() {
        updateState { it.copy(refreshTrigger = it.refreshTrigger + 1) }
    }
    // endregion

    fun sendIntent(intent: PlaylistScreenIntent) {
        when (intent) {
            // Auth & data
            is PlaylistScreenIntent.FetchAccessToken ->
                updateState { it.copy(accessToken = intent.token) }
            is PlaylistScreenIntent.SetPlaylistUrn ->
                updateState { it.copy(playlistUrn = intent.urn) }
            PlaylistScreenIntent.RefreshTracks -> refreshTracks()

            // UX state
            PlaylistScreenIntent.ChangeDidVibrate ->
                updateState { it.copy(didVibrate = !it.didVibrate) }
        }
    }
}
