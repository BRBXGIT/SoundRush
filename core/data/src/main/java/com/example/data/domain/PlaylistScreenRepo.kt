package com.example.data.domain

import com.example.network.playlist_screen.models.PlaylistResponse
import retrofit2.Response

interface PlaylistScreenRepo {

    suspend fun getPlaylist(
        oAuthToken: String,
        playlistId: Int
    ): Response<PlaylistResponse>
}