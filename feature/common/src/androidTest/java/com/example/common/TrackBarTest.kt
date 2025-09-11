package com.example.common

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.common.nav_bar.TrackBar
import com.example.common.nav_bar.TrackBarConstants
import com.example.design_system.theme.SoundRushTheme
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TrackBarTest {

    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun trackBar_shows_labels_and_call_onPlayClick() {
        val author = "ROCKET"
        val name = "Paranoia"

        var clicked = 0

        composeTestRule.setContent {
            SoundRushTheme {
                TrackBar(
                    posterPath = "",
                    author = author,
                    name = name,
                    isPlaying = false,
                    onPlayClick = { clicked = 1 }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(author).assertIsDisplayed()
        composeTestRule.onNodeWithText(name).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TrackBarConstants.PLAY_PAUSE_TEST_TAG).performClick()

        assertEquals(1, clicked)
    }
}