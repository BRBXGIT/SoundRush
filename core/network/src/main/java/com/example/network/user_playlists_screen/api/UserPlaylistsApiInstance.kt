package com.example.network.user_playlists_screen.api

import com.example.network.user_playlists_screen.models.UserPlaylistsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserPlaylistsApiInstance {

    @GET("me/playlists")
    suspend fun getUserPlaylists(
        @Header("Authorization") oAuthToken: String,
        @Query("show_tracks") showTracks: Boolean,
        @Query("linked_partitioning") linkedPartitioning: Boolean,
        @Query("limit") limit: Int
    ): Response<UserPlaylistsResponse>
}