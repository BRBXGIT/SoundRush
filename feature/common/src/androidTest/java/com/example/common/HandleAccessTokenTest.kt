package com.example.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.common.utils.HandleAccessToken
import com.example.design_system.theme.SoundRushTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class HandleAccessTokenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun handleAccessTokenCallsOnHandle() {
        var handled = 0

        composeTestRule.setContent {
            SoundRushTheme {
                val token = "123"

                HandleAccessToken(
                    accessToken = token,
                    onHandle = { handled = 1 }
                )
            }
        }

        composeTestRule.waitForIdle()

        assertEquals(1, handled)
    }
}