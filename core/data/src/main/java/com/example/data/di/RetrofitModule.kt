package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("auth_api")
    fun provideAuthRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://secure.soundcloud.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @Named("main_api")
    fun provideMainRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.soundcloud.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}