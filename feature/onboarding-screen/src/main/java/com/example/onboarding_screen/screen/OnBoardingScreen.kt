package com.example.onboarding_screen.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.theme.ScreenDimens
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.AuthButton
import com.example.onboarding_screen.sections.OnBoardingHeader

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenVM,
    accessToken: String?
) {
    LaunchedEffect(accessToken) {
        if(accessToken != null) {
            viewModel.sendIntent(OnBoardingScreenIntent.SaveAccessToken(accessToken))
        }
    }

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
                    start = ScreenDimens.HorizontalPadding,
                    end = ScreenDimens.HorizontalPadding
                )
        ) {
            OnBoardingHeader()

            Column(
                verticalArrangement = Arrangement.spacedBy(OnBoardingScreenUtils.AboutAppAuthButtonSpacer)
            ) {
                AboutAppSection()

                val context = LocalContext.current
                val authLink = "https://secure.soundcloud.com/authorize?" +
                        "client_id=${OnBoardingScreenUtils.CLIENT_ID}&" +
                        "redirect_uri=${OnBoardingScreenUtils.REDIRECT_URI}&" +
                        "response_type=${OnBoardingScreenUtils.RESPONSE_TYPE}&" +
                        "code_challenge=${OnBoardingScreenUtils.CODE_CHALLENGE}&" +
                        "code_challenge_method=${OnBoardingScreenUtils.CODE_CHALLENGE_METHOD}&" +
                        "state=${OnBoardingScreenUtils.STATE}"
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    authLink.toUri()
                )
                AuthButton(
                    onClick = {
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Preview()
@Composable
fun OnBoardingScreenPreview() {
    SoundRushTheme {
        val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

        OnBoardingScreen(
            viewModel = onBoardingScreenVM,
            accessToken = ""
        )
    }
}