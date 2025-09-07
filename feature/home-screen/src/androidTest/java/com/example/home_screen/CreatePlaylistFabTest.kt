package com.example.home_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.sections.CreatePlaylistBSConstants
import com.example.home_screen.sections.CreatePlaylistFab
import com.example.home_screen.sections.CreatePlaylistFabConstants
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class CreatePlaylistFabTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun createPlaylistFabCallsOnClick() {
        var clicked = 0

        composeTestRule.setContent {
            SoundRushTheme {
                CreatePlaylistFab(
                    onClick = { clicked = 1 },
                    isInDeleteMode = false
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(CreatePlaylistFabConstants.CREATE_PLAYLIST_FAB_TEST_TAG).performClick()
        assertEquals(1, clicked)
    }
}