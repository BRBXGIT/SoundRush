package com.example.onboardng_screen.screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.UiConstants
import com.example.onboardng_screen.sections.AboutAppSection
import com.example.onboardng_screen.sections.ScreenHeader
import com.example.onboardng_screen.sections.StartButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingScreenVM,
    code: String?,
) {
    LaunchedEffect(code) {
        if (code != null) {
            viewModel.sendIntent(
                OnboardingScreenIntent.GetTokens(
                    code = code
                )
            )
        }
    }

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    val screenState by viewModel.onboardingScreenState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { snackbarHostState }
    ) { innerPadding ->
        PullToRefreshBox(
            onRefresh = {},
            isRefreshing = screenState.isLoading
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(SoundRushIcons.OnboardingScreenBg),
                        contentScale = ContentScale.Crop
                    )
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = UiConstants.HORIZONTAL_PADDING.dp,
                        end = UiConstants.HORIZONTAL_PADDING.dp
                    )
            ) {
                ScreenHeader()

                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AboutAppSection()

                    val context = LocalContext.current
                    val url = "https://secure.soundcloud.com/authorize?" +
                            "client_id=${Utils.CLIENT_ID}&" +
                            "redirect_uri=${Utils.REDIRECT_URI}&" +
                            "response_type=${Utils.RESPONSE_TYPE}&" +
                            "code_challenge=${Utils.CODE_CHALLENGE}&" +
                            "code_challenge_method=${Utils.CODE_CHALLENGE_METHOD}&" +
                            "state=$code"
                    StartButton(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    url.toUri()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}