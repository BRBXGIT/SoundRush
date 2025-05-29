package com.example.data.di

import android.content.Context
import com.example.data.data.OnBoardingScreenRepoImpl
import com.example.data.domain.OnBoardingScreenRepo
import com.example.local.datastore.auth.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingScreenModule {

    @Provides
    @Singleton
    fun provideAuthManger(
        @ApplicationContext context: Context
    ): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideOnBoardingScreenRepo(authManager: AuthManager): OnBoardingScreenRepo {
        return OnBoardingScreenRepoImpl(authManager)
    }
}