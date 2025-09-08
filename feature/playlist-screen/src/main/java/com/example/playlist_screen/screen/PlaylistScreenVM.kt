package com.example.playlist_screen.screen

import androidx.lifecycle.ViewModel
import com.example.data.domain.PlaylistScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenVM @Inject constructor(
    private val repo: PlaylistScreenRepo
): ViewModel() {


}