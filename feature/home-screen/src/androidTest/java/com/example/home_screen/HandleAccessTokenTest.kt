package com.example.home_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.screen.HandleAccessToken
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenVM
import org.junit.Rule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class HandleAccessTokenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val vm: HomeScreenVM = mockk(relaxed = true)

    @Test
    fun whenAccessTokenProvided_sendIntentIsCalled() {
        val token = "test_token"
        composeTestRule.setContent {
            SoundRushTheme {
                HandleAccessToken(
                    accessToken = token,
                    viewModel = vm
                )
            }
        }
        composeTestRule.waitForIdle()

        verify { vm.sendIntent(HomeScreenIntent.FetchAccessToken(token)) }
    }

    @Test
    fun whenAccessTokenNull_sendIntentIsNotCalled() {
        composeTestRule.setContent {
            SoundRushTheme {
                HandleAccessToken(
                    accessToken = null,
                    viewModel = vm
                )
            }
        }

        composeTestRule.waitForIdle()

        verify(exactly = 0) { vm.sendIntent(any()) }
    }
}