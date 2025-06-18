package com.example.network.playlist_screen.api

import com.example.network.playlist_screen.models.PlaylistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlaylistScreenApiInstance {

    @GET("playlists/{playlist_id}")
    suspend fun getPlaylist(
        @Header("Authorization") oAuthToken: String,
        @Path("playlist_id") playlistId: Int
    ): Response<PlaylistResponse>
}