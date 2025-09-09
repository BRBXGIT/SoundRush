package com.example.design_system

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.design_system.cards.track_card.TrackCard
import com.example.design_system.cards.track_card.TrackCardConstants
import com.example.design_system.cards.track_card.formatDuration
import com.example.design_system.theme.SoundRushTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class TrackCardTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun trackCard_show_name_author_duration_convert_millis_and_calls_onClick() {
        val name = "В миноре (Prod. by Wex & Lawzy)"
        val author = "Heronwater"
        val duration = 132049
        var clicked = 0

        composeTestRule.setContent {
            SoundRushTheme {
                TrackCard(
                    posterPath = "",
                    name = name,
                    author = author,
                    duration = duration,
                    onClick = { clicked = 1 }
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(name).assertIsDisplayed()

        val convertedMillis = formatDuration(duration)
        assertEquals("2:12", convertedMillis)

        composeTestRule.onNodeWithText("$author • $convertedMillis").assertIsDisplayed()

        composeTestRule.onNodeWithTag(TrackCardConstants.SURFACE_TEST_TAG).performClick()

        assertEquals(1, clicked)
    }
}