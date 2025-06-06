package com.example.navbar_screens.user_playlists_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.SoundRushDispatchers
import com.example.common.utils.AuthUtils
import com.example.data.domain.AuthRepo
import com.example.data.domain.UserPlaylistsScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
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
        .distinctUntilChanged()
        .flatMapLatest { token ->
            repository.getUserPlaylists(token)
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

    private fun refreshUserTokens() {
        viewModelScope.launch(dispatcherIo) {
            authRepository.refreshUserTokens(
                clientId = AuthUtils.CLIENT_ID,
                clientSecret = AuthUtils.CLIENT_SECRET,
                refreshToken = _userPlaylistsScreenState.value.refreshToken!!
            )
        }
    }

    fun sendIntent(intent: UserPlaylistsScreenIntent) {
        when (intent) {
            is UserPlaylistsScreenIntent.RefreshUserTokens -> refreshUserTokens()
        }
    }

    init {
        fetchTokens()
    }
}