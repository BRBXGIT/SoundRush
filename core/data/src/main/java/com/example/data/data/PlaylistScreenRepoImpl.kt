package com.example.data.data

import com.example.data.domain.PlaylistScreenRepo
import com.example.network.playlist_screen.api.PlaylistScreenApiInstance
import com.example.network.playlist_screen.models.PlaylistResponse
import retrofit2.Response
import javax.inject.Inject

class PlaylistScreenRepoImpl @Inject constructor(
    private val apiInstance: PlaylistScreenApiInstance
): PlaylistScreenRepo {

    override suspend fun getPlaylist(
        oAuthToken: String,
        playlistId: Int
    ): Response<PlaylistResponse> {
        return apiInstance.getPlaylist(
            oAuthToken = oAuthToken,
            playlistId = playlistId
        )
    }
}