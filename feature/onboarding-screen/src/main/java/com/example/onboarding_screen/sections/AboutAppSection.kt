package com.example.onboarding_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mTypography
import com.example.onboarding_screen.screen.OnBoardingScreenUtils

@Composable
fun AboutAppSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(OnBoardingScreenUtils.HelloStringAppDescriptionSpacer)
    ) {
        Text(
            text = "Hello in SoundRush",
            style = mTypography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "SoundRush is an unofficial android client for SoundCloud. In this app you can track, " +
                    "experience and listen music",
            style = mTypography.bodyLarge
        )
    }
}

@Preview
@Composable
fun AboutAppSectionPreview() {
    SoundRushTheme {
        AboutAppSection()
    }
}