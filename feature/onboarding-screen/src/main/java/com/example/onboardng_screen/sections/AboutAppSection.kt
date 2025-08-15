package com.example.onboardng_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mTypography

@Composable
fun AboutAppSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Hello in SoundRush",
            style = mTypography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "SoundRush is an app where you can listen, save and explore music",
            style = mTypography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun AboutAppSectionPreview() {
    SoundRushTheme {
        AboutAppSection()
    }
}