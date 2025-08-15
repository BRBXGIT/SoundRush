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
import com.example.onboardng_screen.screen.Strings

@Composable
fun AboutAppSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = Strings.HELLO_IN,
            style = mTypography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = Strings.ABOUT_APP,
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