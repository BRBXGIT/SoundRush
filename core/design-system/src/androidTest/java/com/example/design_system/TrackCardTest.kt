package com.example.design_system

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.cards.track_card.TrackCard
import com.example.design_system.cards.track_card.formatDuration
import com.example.design_system.theme.SoundRushTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class TrackCardTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun trackCard_show_name_author_duration_and_convert_millis() {
        val name = "В миноре (Prod. by Wex & Lawzy)"
        val author = "Heronwater"
        val duration = 132049

        composeTestRule.setContent {
            SoundRushTheme {
                TrackCard(
                    posterPath = "",
                    name = name,
                    author = author,
                    duration = duration
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(name).assertIsDisplayed()

        val convertedMillis = formatDuration(duration)
        assertEquals("2:12", convertedMillis)

        composeTestRule.onNodeWithText("$author • $convertedMillis").assertIsDisplayed()
    }
}