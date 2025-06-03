package com.example.onboarding_screen.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.theme.ScreenDimens
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mColors
import com.example.onboarding_screen.sections.AboutAppSection
import com.example.onboarding_screen.sections.AuthButton
import com.example.onboarding_screen.sections.OnBoardingHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenVM,
    code: String?,
    state: String?,
    screenState: OnBoardingScreenState
) {
    LaunchedEffect(code, state) {
        if((code != null) and (state != null)) {
            viewModel.sendIntent(
                OnBoardingScreenIntent.FetchUserTokens(
                    clientId = OnBoardingScreenUtils.CLIENT_ID,
                    clientSecret = OnBoardingScreenUtils.CLIENT_SECRET,
                    redirectUri = OnBoardingScreenUtils.REDIRECT_URI,
                    codeVerifier = OnBoardingScreenUtils.CODE_VERIFIER,
                    code = code!!
                )
            )
        }
    }

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    PullToRefreshBox(
        onRefresh = {},
        isRefreshing = screenState.isUserTokensLoading
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
}

@Preview()
@Composable
fun OnBoardingScreenPreview() {
    SoundRushTheme {
        val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

        OnBoardingScreen(
            viewModel = onBoardingScreenVM,
            code = null,
            state = null,
            screenState = OnBoardingScreenState()
        )
    }
}