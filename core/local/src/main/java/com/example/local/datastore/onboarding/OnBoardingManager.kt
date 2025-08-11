package com.example.local.datastore.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnBoardingManager(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(name = "sound_rush_onboarding_prefs")

    // Keys
    companion object {
        private val IS_ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("is_onboarding_completed")
    }

    val isOnboardingCompletedFlow: Flow<Boolean?> = context.dataStore.data
        .map { preferences -> preferences[IS_ONBOARDING_COMPLETED_KEY] }

    suspend fun saveOnBoardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences[IS_ONBOARDING_COMPLETED_KEY] = true
        }
    }

    suspend fun clearIsOnBoardingCompleted() {
        context.dataStore.edit { preferences ->
            preferences.remove(IS_ONBOARDING_COMPLETED_KEY)
        }
    }
}