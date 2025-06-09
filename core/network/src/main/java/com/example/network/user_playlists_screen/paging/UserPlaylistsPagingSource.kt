package com.example.network.user_playlists_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.network.user_playlists_screen.api.UserPlaylistsApiInstance
import com.example.network.user_playlists_screen.models.user_playlists_response.Collection
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

class UserPlaylistsPagingSource(
    private val apiInstance: UserPlaylistsApiInstance,
    private val oAuthToken: String
) : PagingSource<String, Collection>() {

    override fun getRefreshKey(state: PagingState<String, Collection>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Collection> {
        return try {
            val response = if (params.key == null) {
                apiInstance.getUserPlaylists(
                    oAuthToken = oAuthToken
                )
            } else {
                apiInstance.getPlaylistByHref(
                    oAuthToken = oAuthToken,
                    nextHref = params.key!!
                )
            }

            if (response.code() == 200) {
                val playlists = response.body()
                LoadResult.Page(
                    data = playlists?.collection ?: emptyList(),
                    prevKey = null,
                    nextKey = playlists?.nextHref
                )
            } else {
                val exception = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(exception)
                LoadResult.Error(NetworkException(exception, label))
            }
        } catch (e: IOException) {
            if (e is UnknownHostException || e is SocketException) {
                val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
                LoadResult.Error(NetworkException(NetworkErrors.INTERNET, label))
            } else {
                val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
                LoadResult.Error(NetworkException(NetworkErrors.UNAUTHORIZED, label))
            }
        }
    }
}
