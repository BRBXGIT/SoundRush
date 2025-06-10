package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.UserPlaylistsScreenRepo
import com.example.network.user_playlists_screen.api.UserPlaylistsApiInstance
import com.example.network.user_playlists_screen.models.create_playlist_request.CreatePlaylistRequest
import com.example.network.user_playlists_screen.models.create_playlist_request.Playlist
import com.example.network.user_playlists_screen.models.create_playlist_request.Track
import com.example.network.user_playlists_screen.models.create_playlist_response.CreatePlaylistResponse
import com.example.network.user_playlists_screen.models.user_playlists_response.Collection
import com.example.network.user_playlists_screen.paging.UserPlaylistsPagingSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserPlaylistsScreenRepoImpl @Inject constructor(
    private val apiInstance: UserPlaylistsApiInstance
): UserPlaylistsScreenRepo {

    override fun getUserPlaylists(oAuthToken: String): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { UserPlaylistsPagingSource(apiInstance, oAuthToken) }
        ).flow
    }

    override suspend fun createPlaylist(
        title: String,
        description: String,
        oAuthToken: String,
    ): Response<CreatePlaylistResponse> {
        val request = CreatePlaylistRequest(
            playlist = Playlist(
                description = description,
                title = title,
                tracks = listOf(
                    Track(id = 1)
                )
            )
        )

        return apiInstance.createPlaylist(request, oAuthToken)
    }
}