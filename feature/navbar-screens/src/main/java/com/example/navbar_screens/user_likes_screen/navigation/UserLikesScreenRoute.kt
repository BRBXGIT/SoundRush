package com.example.navbar_screens.user_likes_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.user_likes_screen.screen.UserLikesScreen
import kotlinx.serialization.Serializable

@Serializable
data object UserLikesScreenRoute

fun NavGraphBuilder.userLikesScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<UserLikesScreenRoute> {
    UserLikesScreen(
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}