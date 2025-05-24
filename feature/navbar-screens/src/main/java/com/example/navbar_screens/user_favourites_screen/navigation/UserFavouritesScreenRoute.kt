package com.example.navbar_screens.user_favourites_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbar_screens.user_favourites_screen.screen.UserFavouritesScreen
import kotlinx.serialization.Serializable

@Serializable
data object UserFavouritesScreenRoute

fun NavGraphBuilder.userFavouritesScreen() = composable<UserFavouritesScreenRoute> {
    UserFavouritesScreen()
}