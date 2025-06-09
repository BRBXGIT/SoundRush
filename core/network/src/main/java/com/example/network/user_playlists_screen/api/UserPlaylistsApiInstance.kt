package com.example.network.user_playlists_screen.api

import com.example.network.user_playlists_screen.models.create_playlist_request.CreatePlaylistRequest
import com.example.network.user_playlists_screen.models.create_playlist_response.CreatePlaylistResponse
import com.example.network.user_playlists_screen.models.user_playlists_response.UserPlaylistsResponse
import com.example.network.user_playlists_screen.paging.UserPlaylistsUtils
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface UserPlaylistsApiInstance {

    @GET("me/playlists")
    suspend fun getUserPlaylists(
        @Header("Authorization") oAuthToken: String,
        @Query("show_tracks") showTracks: Boolean = false,
        @Query("linked_partitioning") linkedPartitioning: Boolean = true,
        @Query("limit") limit: Int = UserPlaylistsUtils.LIMIT
    ): Response<UserPlaylistsResponse>

    @GET
    suspend fun getPlaylistByHref(
        @Header("Authorization") oAuthToken: String,
        @Url nextHref: String
    ): Response<UserPlaylistsResponse>

    @POST("playlists")
    suspend fun createPlaylist(
        @Body createPlaylistRequest: CreatePlaylistRequest,
        @Header("Authorization") oAuthToken: String,
    ): Response<CreatePlaylistResponse>
}