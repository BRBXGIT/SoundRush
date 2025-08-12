package com.example.onboardng_screen.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.design_system.theme.mColors

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingScreenVM,
    state: String?,
    error: String?
) {
    LaunchedEffect(state, error) {
        Log.d("CCCC", "error: $error, state: $state")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            Text(
                text = "Onboarding screen"
            )
        }
    }
}