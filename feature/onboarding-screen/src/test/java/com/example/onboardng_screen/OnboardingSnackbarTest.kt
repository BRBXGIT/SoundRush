package com.example.onboardng_screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.theme.SoundRushTheme
import com.example.onboardng_screen.screen.OnboardingScreen
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class OnboardingSnackbarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `when error nonNull calls SnackbarController sendEvent`() = runTest {
        mockkObject(SnackbarController)

        composeTestRule.setContent {
            SoundRushTheme {
                OnboardingScreen(
                    navController = rememberNavController(),
                    viewModel = mockk(relaxed = true),
                    state = null,
                    error = "SOME ERROR"
                )
            }
        }

        composeTestRule.waitForIdle()

        coVerify { SnackbarController.sendEvent(match { it.message == "SOME ERROR" }) }
    }
}