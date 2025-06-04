package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.user_playlists_screen.models.Collection
import kotlinx.coroutines.flow.Flow

interface UserPlaylistsScreenRepo {

    fun getTrendingAnimeList(oAuthToken: String): Flow<PagingData<Collection>>
}