package com.example.data.di

import com.example.data.domain.AppStartingRepo
import com.example.data.repositories.AppStartingRepoImpl
import com.example.local.datastore.onboarding.OnboardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppStartingModule {

    @Provides
    @Singleton
    fun provideAppStartingRepo(onboardingManager: OnboardingManager): AppStartingRepo {
        return AppStartingRepoImpl(onboardingManager)
    }
}