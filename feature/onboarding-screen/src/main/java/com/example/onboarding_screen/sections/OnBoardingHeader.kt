package com.example.onboarding_screen.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.onboarding_screen.screen.OnBoardingScreenUtils

@Composable
fun OnBoardingHeader() {
    val annotatedSoundRushString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = mColors.primary,
                fontSize = OnBoardingScreenUtils.AppNameStringHeight,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("SoundRush\n")
        }
        withStyle(
            style = SpanStyle(
                color = mColors.onBackground,
                fontSize = OnBoardingScreenUtils.AppNamePoweredByStringHeight,
            )
        ) {
            append("Powered by SoundCloud")
        }
    }

    Text(
        text = annotatedSoundRushString
    )
}

@Preview()
@Composable
fun OnBoardingHeaderPreview() {
    SoundRushTheme {
        OnBoardingHeader()
    }
}