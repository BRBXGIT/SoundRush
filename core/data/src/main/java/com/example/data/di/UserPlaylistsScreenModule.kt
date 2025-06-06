package com.example.data.di

import com.example.data.data.UserPlaylistsScreenRepoImpl
import com.example.data.domain.UserPlaylistsScreenRepo
import com.example.network.user_playlists_screen.api.UserPlaylistsApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserPlaylistsScreenModule {

    @Provides
    @Singleton
    fun provideUserPlaylistsScreenApiInstance(@Named("main_api") retrofit: Retrofit): UserPlaylistsApiInstance {
        return retrofit.create(UserPlaylistsApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPlaylistsScreenRepo(apiInstance: UserPlaylistsApiInstance): UserPlaylistsScreenRepo {
        return UserPlaylistsScreenRepoImpl(apiInstance)
    }
}