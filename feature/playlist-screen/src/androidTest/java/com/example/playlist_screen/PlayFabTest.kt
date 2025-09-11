package com.example.playlist_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.SoundRushTheme
import com.example.playlist_screen.sections.PlayFab
import com.example.playlist_screen.sections.PlayFabConstants
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class PlayFabTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun playFab_calls_onClick() {
        var clicked = 0

        composeTestRule.setContent {
            SoundRushTheme {
                PlayFab(
                    onClick = { clicked = 1 },
                    isPlaying = false
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(PlayFabConstants.PLAY_FAB_TEST_TAG).performClick()
        assertEquals(1, clicked)
    }
}