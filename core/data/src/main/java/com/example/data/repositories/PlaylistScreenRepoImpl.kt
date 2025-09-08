package com.example.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.PlaylistScreenRepo
import com.example.network.common.NetworkUtils
import com.example.network.playlist_screen.api.PlaylistScreenApiInstance
import com.example.network.playlist_screen.models.Collection
import com.example.network.playlist_screen.paging.PlaylistTracksPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistScreenRepoImpl @Inject constructor(
    private val apiInstance: PlaylistScreenApiInstance
): PlaylistScreenRepo {

    override fun getPlaylistTracks(accessToken: String?, playlistUrn: String): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = NetworkUtils.LIMIT, enablePlaceholders = false),
            pagingSourceFactory = { PlaylistTracksPagingSource(apiInstance, accessToken, playlistUrn) }
        ).flow
    }
}