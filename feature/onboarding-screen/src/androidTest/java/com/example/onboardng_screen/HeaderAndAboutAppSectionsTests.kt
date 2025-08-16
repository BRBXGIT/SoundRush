package com.example.onboardng_screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.theme.SoundRushTheme
import com.example.onboardng_screen.screen.Strings
import com.example.onboardng_screen.sections.AboutAppSection
import com.example.onboardng_screen.sections.ScreenHeader
import org.junit.Rule
import org.junit.Test

class HeaderAndAboutAppSectionsTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenHeader_display_title_and_subtitle() {
        composeTestRule.setContent {
            SoundRushTheme {
                ScreenHeader()
            }
        }

        composeTestRule.onNodeWithTag("screenHeader").assertIsDisplayed()
    }

    @Test
    fun aboutSection_displays_heading_and_description() {
        composeTestRule.setContent {
            SoundRushTheme {
                AboutAppSection()
            }
        }

        composeTestRule.onNodeWithText(Strings.HELLO_IN).assertIsDisplayed()
        composeTestRule.onNodeWithText(Strings.ABOUT_APP).assertIsDisplayed()
    }
}
