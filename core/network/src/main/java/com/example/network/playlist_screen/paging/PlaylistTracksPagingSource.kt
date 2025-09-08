package com.example.network.playlist_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkException
import com.example.network.common.NetworkRequest
import com.example.network.playlist_screen.api.PlaylistScreenApiInstance
import com.example.network.playlist_screen.models.Collection

class PlaylistTracksPagingSource(
    private val api: PlaylistScreenApiInstance,
    private val accessToken: String
): PagingSource<String, Collection>() {

    override fun getRefreshKey(state: PagingState<String, Collection>): String? {
        val anchor = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchor)
        return anchorPage?.prevKey ?: anchorPage?.nextKey
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Collection> {
        val response = if (params.key == null) {
            NetworkRequest.exec { api.getPlaylistTracks(accessToken) }
        } else {
            NetworkRequest.exec { api.getPlaylistTracksNext(accessToken, params.key!!) }
        }

        return if (response.error == NetworkErrors.SUCCESS) {
            val result = response.response!!
            LoadResult.Page(
                data = result.collection,
                prevKey = null,
                nextKey = result.nextHref.takeIf { !it.isNullOrBlank() }
            )
        } else {
            LoadResult.Error(NetworkException(response.error!!, response.label!!))
        }
    }
}