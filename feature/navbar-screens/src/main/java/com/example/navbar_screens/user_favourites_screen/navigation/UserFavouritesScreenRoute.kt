package com.example.navbar_screens.user_favourites_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.user_favourites_screen.screen.UserFavouritesScreen
import kotlinx.serialization.Serializable

@Serializable
data object UserFavouritesScreenRoute

fun NavGraphBuilder.userFavouritesScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<UserFavouritesScreenRoute> {
    UserFavouritesScreen(
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}