package com.example.data.di

import com.example.data.domain.PlaylistScreenRepo
import com.example.data.repositories.PlaylistScreenRepoImpl
import com.example.network.playlist_screen.api.PlaylistScreenApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaylistScreenModule {

    @Provides
    @Singleton
    fun providePlaylistScreenApiInstance(@Named("main_api") retrofit: Retrofit): PlaylistScreenApiInstance {
        return retrofit.create(PlaylistScreenApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun providePlaylistScreenRepo(apiInstance: PlaylistScreenApiInstance): PlaylistScreenRepo {
        return PlaylistScreenRepoImpl(apiInstance)
    }
}