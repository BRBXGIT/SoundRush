package com.example.soundrush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.theme.SoundRushTheme
import com.example.local.datastore.auth.AuthState
import com.example.onboarding_screen.navigation.OnBoardingScreenRoute
import com.example.playlist_screen.navigation.PlaylistScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avoid bug when theme doesn't want to change after splashscreen
        setTheme(R.style.Theme_SoundRush)

        enableEdgeToEdge()

        setContent {
            var isLoggedIn by rememberSaveable { mutableStateOf(false) }
            val authVM = hiltViewModel<AuthVM>()
            val authState by authVM.authState.collectAsStateWithLifecycle()

            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition {
                when(authState) {
                    AuthState.Loading -> true
                    AuthState.LoggedOut -> false
                    AuthState.LoggedIn -> {
                        isLoggedIn = true
                        false // Remove splashscreen
                    }
                }
            }

            SoundRushTheme {
                NavGraph(
                    startDestination = if (isLoggedIn) PlaylistScreenRoute else OnBoardingScreenRoute
                )
            }
        }
    }
}