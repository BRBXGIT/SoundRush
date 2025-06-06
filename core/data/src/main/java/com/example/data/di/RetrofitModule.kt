package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    // Gson and retrofit need's to be provided only one time to be in singleton scope
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(gson: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://secure.soundcloud.com/")
            .addConverterFactory(gson)
            .build()

    @Provides
    @Singleton
    @Named("main_api")
    fun provideUserPlaylistsRetrofit(gson: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.soundcloud.com/")
            .addConverterFactory(gson)
            .build()
}