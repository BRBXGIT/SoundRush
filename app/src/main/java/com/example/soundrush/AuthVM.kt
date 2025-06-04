package com.example.soundrush

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AuthVM @Inject constructor(
    authRepo: AuthRepo
): ViewModel() {
    val authState = authRepo.authState
        .onStart { emit(AuthState.Loading) }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            AuthState.Loading
        )
}