package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.home_screen.models.user_playlists_response.Collection
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    fun getPlaylists(accessToken: String): Flow<PagingData<Collection>>
}