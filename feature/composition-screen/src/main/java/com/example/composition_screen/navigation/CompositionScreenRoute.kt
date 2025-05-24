package com.example.composition_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.composition_screen.screen.CompositionScreen
import kotlinx.serialization.Serializable

@Serializable
data object CompositionScreenRoute

fun NavGraphBuilder.compositionScreen() = composable<CompositionScreenRoute> {
    CompositionScreen()
}