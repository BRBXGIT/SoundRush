package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.user_playlists_screen.models.create_playlist_request.CreatePlaylistRequest
import com.example.network.user_playlists_screen.models.create_playlist_response.CreatePlaylistResponse
import com.example.network.user_playlists_screen.models.user_playlists_response.Collection
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserPlaylistsScreenRepo {

    fun getUserPlaylists(oAuthToken: String): Flow<PagingData<Collection>>

    suspend fun createPlaylist(
        title: String,
        description: String,
        oAuthToken: String,
    ): Response<CreatePlaylistResponse>
}