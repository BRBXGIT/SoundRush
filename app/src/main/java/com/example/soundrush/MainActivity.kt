package com.example.soundrush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.utils.OnBoardingState
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.navigation.HomeScreenRoute
import com.example.onboardng_screen.navigation.OnboardingScreenRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appStartingVM = hiltViewModel<AppStartingVM>()
            val onboardingState by appStartingVM.onboardingState.collectAsStateWithLifecycle()

            SoundRushTheme {
                when(onboardingState) {
                    OnBoardingState.Completed -> NavGraph(HomeScreenRoute)
                    OnBoardingState.Loading -> {}
                    OnBoardingState.NotCompleted -> NavGraph(OnboardingScreenRoute())
                }
            }
        }
    }
}