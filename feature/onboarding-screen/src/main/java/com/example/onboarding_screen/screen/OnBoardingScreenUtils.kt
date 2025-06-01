package com.example.onboarding_screen.screen

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object OnBoardingScreenUtils {
    val VerticalPadding = 16.dp

    val AppNameStringHeight = 24.sp
    val AppNamePoweredByStringHeight = 16.sp

    val HelloStringAppDescriptionSpacer = 12.dp // Spacer between hello and app desc.
    val AboutAppAuthButtonSpacer = 24.dp

    val AuthButtonSCLogoAuthTextSpacer = 8.dp //Spacer between sc icon and auth label
    val SCIconSize = 20.dp

    // TODO rewrite to encrypted
    const val CLIENT_ID = "6gzvoKOdbET4tkbPsZsJDg6QhaYXtNoZ"
    const val REDIRECT_URI = "https://sound-rush.com/"
    const val RESPONSE_TYPE = "code"
    const val CODE_CHALLENGE = "vYADCzxvYHc5oymZLHgyKUQ6fOFNcrCDnmq1aTo_I_I"
    const val CODE_CHALLENGE_METHOD = "S256"
    const val STATE = "62790beb52ab211c4ad5c93dad15ba3de8854cb7f86a5785489adefa"

    const val BASIC_DEEP_LINK_DOMAIN = "soundrush-6c78e.web.app"
}