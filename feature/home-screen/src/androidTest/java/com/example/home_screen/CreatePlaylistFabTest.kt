package com.example.home_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.screen.HomeScreenUtils
import com.example.home_screen.sections.CreatePlaylistFab
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class CreatePlaylistFabTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun create_playlist_fab_calls_on_click() {
        var number = 0

        composeTestRule.setContent {
            SoundRushTheme {
                CreatePlaylistFab(
                    onClick = { number = 1 }
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(HomeScreenUtils.CREATE_PLAYLIST_FAB_TEST_TAG).performClick()

        assertEquals(1, number)
    }
}