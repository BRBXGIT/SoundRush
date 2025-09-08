package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.playlist_screen.models.Collection
import kotlinx.coroutines.flow.Flow

interface PlaylistScreenRepo {

    fun getPlaylistTracks(accessToken: String): Flow<PagingData<Collection>>
}