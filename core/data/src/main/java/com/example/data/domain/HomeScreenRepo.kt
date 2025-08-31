package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.common.NetworkResponse
import com.example.network.home_screen.models.create_playlist.create_playlist_response.CreatePlaylistResponse
import com.example.network.home_screen.models.user_playlists_response.Collection
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    fun getPlaylists(accessToken: String): Flow<PagingData<Collection>>

    suspend fun createPlaylist(
        accessToken: String?,
        playlistName: String,
        description: String
    ): NetworkResponse<CreatePlaylistResponse>
}