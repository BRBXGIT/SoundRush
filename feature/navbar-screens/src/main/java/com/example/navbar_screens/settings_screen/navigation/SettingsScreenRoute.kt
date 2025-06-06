package com.example.navbar_screens.settings_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbar_screens.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen() = composable<SettingsScreenRoute> {
    SettingsScreen()
}