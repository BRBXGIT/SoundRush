package com.example.data.di

import android.content.Context
import com.example.local.datastore.onboarding.OnBoardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideOnboardingManager(@ApplicationContext context: Context) = OnBoardingManager(context)
}