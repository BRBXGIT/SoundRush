package com.example.onboarding_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mShapes
import com.example.onboarding_screen.screen.OnBoardingScreenUtils

@Composable
fun AuthButton() {
    Button(
        shape = mShapes.small,
        onClick = {  },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(OnBoardingScreenUtils.AuthButtonSCLogoAuthTextSpacer)
        ) {
            Icon(
                painter = painterResource(SoundRushIcons.SoundCloudMulticolored),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(OnBoardingScreenUtils.SCIconSize)
            )

            Text(
                text = "Authenticate with SoundCloud",
            )
        }
    }
}

@Preview
@Composable
fun AuthButtonPreview() {
    SoundRushTheme {
        AuthButton()
    }
}