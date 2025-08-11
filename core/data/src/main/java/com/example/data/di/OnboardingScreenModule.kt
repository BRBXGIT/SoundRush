package com.example.data.di

import com.example.data.domain.OnBoardingScreenRepo
import com.example.data.repositories.OnBoardingScreenRepoImpl
import com.example.local.datastore.onboarding.OnBoardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingScreenModule {

    @Provides
    @Singleton
    fun provideOnboardingScreenRepo(onBoardingManager: OnBoardingManager): OnBoardingScreenRepo {
        return OnBoardingScreenRepoImpl(onBoardingManager)
    }
}