package com.example.data.di

import com.example.data.data.UserPlaylistsScreenRepoImpl
import com.example.data.domain.UserPlaylistsScreenRepo
import com.example.network.user_playlists_screen.api.UserPlaylistsApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object UserPlaylistsScreenModule {

    @Provides
    @Singleton
    fun provideUserPlaylistsScreenApiInstance(): UserPlaylistsApiInstance {
        return Retrofit.Builder()
            .baseUrl("https://api.soundcloud.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserPlaylistsApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPlaylistsScreenRepo(apiInstance: UserPlaylistsApiInstance): UserPlaylistsScreenRepo {
        return UserPlaylistsScreenRepoImpl(apiInstance)
    }
}