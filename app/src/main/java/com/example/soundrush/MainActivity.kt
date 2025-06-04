package com.example.soundrush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.SoundRushTheme
import com.example.local.datastore.auth.AuthState
import com.example.navbar_screens.user_playlists_screen.navigation.UserPlaylistsScreenRoute
import com.example.onboarding_screen.navigation.OnBoardingScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avoid bug when theme doesn't want to change after splashscreen
        setTheme(R.style.Theme_SoundRush)

        enableEdgeToEdge()

        setContent {
            val authVM = hiltViewModel<AuthVM>()
            val authState by authVM.authState.collectAsStateWithLifecycle()

            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
                authState is AuthState.Loading
            }

            SoundRushTheme {
                if(authState !is AuthState.Loading) {
                    NavGraph(
                        startDestination = if (authState is AuthState.LoggedIn) {
                            UserPlaylistsScreenRoute
                        } else {
                            OnBoardingScreenRoute()
                        },
                    )
                }
            }
        }
    }
}