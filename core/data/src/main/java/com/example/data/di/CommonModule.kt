package com.example.data.di

import com.example.data.domain.CommonRepo
import com.example.data.repositories.CommonRepoImpl
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideCommonRepo(authApiInstance: AuthApiInstance, authManager: AuthManager): CommonRepo {
        return CommonRepoImpl(authApiInstance, authManager)
    }
}