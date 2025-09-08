package com.example.network.playlist_screen.api

import com.example.network.playlist_screen.models.PlaylistTracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface PlaylistScreenApiInstance {

    @GET("playlists/{playlist_urn}/tracks")
    suspend fun getPlaylistTracks(
        @Header("Authorization") accessToken: String,
        @Query("linked_partitioning") linkedPartitioning: Boolean = true,
    ): Response<PlaylistTracksResponse>

    @GET
    suspend fun getPlaylistTracksNext(
        @Header("Authorization") accessToken: String,
        @Url nextHref: String
    ): Response<PlaylistTracksResponse>
}