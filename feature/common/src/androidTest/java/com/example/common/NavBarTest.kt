package com.example.common

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.common.`nav_bar\`.BarsUtils
import com.example.common.`nav_bar\`.NavBar
import com.example.design_system.theme.SoundRushTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class NavBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun navbar_displays_labels_and_calls_on_click() {
        var index = 0

        composeTestRule.setContent {
            SoundRushTheme {
                Box {
                    NavBar(
                        selectedItemIndex = index,
                        onNavItemClick = { _, _ ->
                            index = 1
                        }
                    )
                }
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(BarsUtils.PLAYLISTS).assertIsDisplayed()
        composeTestRule.onNodeWithText(BarsUtils.LIKES).assertIsDisplayed()
        composeTestRule.onNodeWithText(BarsUtils.SETTINGS).assertIsDisplayed()

        composeTestRule.onNodeWithTag(BarsUtils.NAV_BAR_ITEM_TEST_TAG).performClick()

        assertEquals(1, index)
    }
}