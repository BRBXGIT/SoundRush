package com.example.network.home_screen.api

import com.example.network.common.NetworkUtils
import com.example.network.home_screen.models.create_playlist_request.CreatePlaylistRequest
import com.example.network.home_screen.models.user_playlists_response.UserPlaylistsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface HomeScreenApiInstance {

    @GET("me/playlists")
    suspend fun getPlaylists(
        @Header("Authorization") accessToken: String,
        @Query("linked_partitioning") linkedPartitioning: Boolean = true,
        @Query("limit") limit: Int = NetworkUtils.LIMIT
    ): Response<UserPlaylistsResponse>

    @GET
    suspend fun getPlaylistsNext(
        @Header("Authorization") accessToken: String,
        @Url nextHref: String
    ): Response<UserPlaylistsResponse>

    @POST("playlists")
    suspend fun createPlaylist(
        @Header("Authorization") accessToken: String?,
        @Body createPlaylistRequest: CreatePlaylistRequest
    ): Response<Unit>

    @DELETE("playlists/{playlist_urn}")
    suspend fun deletePlaylist(
        @Header("Authorization") accessToken: String?,
        @Path("playlist_urn") playlistUrn: String
    ): Response<Unit>
}