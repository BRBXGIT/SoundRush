package com.example.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.NetworkRequest
import com.example.network.common.NetworkResponse
import com.example.network.common.NetworkUtils
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.home_screen.models.create_playlist.create_playlist_request.CreatePlaylistRequest
import com.example.network.home_screen.models.create_playlist.create_playlist_request.Playlist
import com.example.network.home_screen.models.create_playlist.create_playlist_response.CreatePlaylistResponse
import com.example.network.home_screen.models.user_playlists_response.Collection
import com.example.network.home_screen.paging.UserPlaylistsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeScreenRepoImpl @Inject constructor(
    private val apiInstance: HomeScreenApiInstance
): HomeScreenRepo {

    override fun getPlaylists(accessToken: String): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = NetworkUtils.LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { UserPlaylistsPagingSource(apiInstance, accessToken) }
        ).flow
    }

    override suspend fun createPlaylist(
        accessToken: String?,
        playlistName: String,
        description: String
    ): NetworkResponse<CreatePlaylistResponse> {
        val body = CreatePlaylistRequest(Playlist(title = playlistName, description = description))

        return NetworkRequest.exec { apiInstance.createPlaylist(accessToken, body) }
    }
}