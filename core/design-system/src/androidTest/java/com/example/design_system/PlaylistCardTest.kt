package com.example.design_system

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.cards.PlaylistCard
import com.example.design_system.theme.SoundRushTheme
import org.junit.Rule
import org.junit.Test

class PlaylistCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playlistCard_displays_title_and_playlist_info() {
        val posterPath = ""
        val title = "title"
        val trackCount = 228
        val creator = "BRBX"

        composeTestRule.setContent {
            SoundRushTheme {
                PlaylistCard(
                    posterPath = posterPath,
                    title = title,
                    trackCount = trackCount,
                    creator = creator,
                    onClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText("Playlist • $creator • $trackCount tracks").assertIsDisplayed()
    }
}