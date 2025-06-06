package com.example.data.di

import android.content.Context
import com.example.data.data.AuthRepoImpl
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthManger(
        @ApplicationContext context: Context
    ): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthApiInstance(): AuthApiInstance {
        return Retrofit.Builder()
            .baseUrl("https://secure.soundcloud.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideOnBoardingScreenRepo(
        authManager: AuthManager,
        authApiInstance: AuthApiInstance
    ): AuthRepo {
        return AuthRepoImpl(authManager, authApiInstance)
    }
}