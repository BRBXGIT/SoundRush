package com.example.data.di

import com.example.data.domain.OnboardingScreenRepo
import com.example.data.repositories.OnboardingScreenRepoImpl
import com.example.local.datastore.onboarding.OnboardingManager
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
    fun provideOnboardingScreenRepo(onBoardingManager: OnboardingManager): OnboardingScreenRepo {
        return OnboardingScreenRepoImpl(onBoardingManager)
    }
}