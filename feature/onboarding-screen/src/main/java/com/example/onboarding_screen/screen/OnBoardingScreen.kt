package com.example.onboarding_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.ScreenDimens
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.AuthButton
import com.example.onboarding_screen.sections.OnBoardingHeader

@Composable
fun OnBoardingScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            mColors.primaryContainer,
                            mColors.secondary
                        )
                    )
                )
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + OnBoardingScreenUtils.VerticalPadding,
                    bottom = innerPadding.calculateBottomPadding() + OnBoardingScreenUtils.VerticalPadding,
                    start = ScreenDimens.horizontalPadding,
                    end = ScreenDimens.horizontalPadding
                )
        ) {
            OnBoardingHeader()

            Column(
                verticalArrangement = Arrangement.spacedBy(OnBoardingScreenUtils.AboutAppAuthButtonSpacer)
            ) {
                AboutAppSection()

                AuthButton()
            }
        }
    }
}

@Preview()
@Composable
fun OnBoardingScreenPreview() {
    SoundRushTheme {
        OnBoardingScreen()
    }
}