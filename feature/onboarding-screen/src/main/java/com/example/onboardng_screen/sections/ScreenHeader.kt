package com.example.onboardng_screen.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors

@Composable
fun ScreenHeader() {
    val annotatedLibriaNowString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = mColors.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("SoundRush\n")
        }
        withStyle(
            style = SpanStyle(
                color = mColors.onBackground,
                fontSize = 16.sp,
            )
        ) {
            append("Powered by SoundCloud api")
        }
    }

    Text(
        text = annotatedLibriaNowString
    )
}

@Preview
@Composable
private fun ScreenHeaderPreview() {
    SoundRushTheme {
        ScreenHeader()
    }
}