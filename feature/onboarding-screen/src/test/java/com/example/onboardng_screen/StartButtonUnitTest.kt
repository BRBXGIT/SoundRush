package com.example.onboardng_screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.SoundRushTheme
import com.example.onboardng_screen.screen.Strings
import com.example.onboardng_screen.sections.StartButton
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class StartButtonUnitTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun`startButton calls onClick`() {
        var clicked = false
        composeTestRule.setContent {
            SoundRushTheme {
                StartButton(onClick = { clicked = true })
            }
        }

        composeTestRule.onNodeWithText(Strings.CONNECT_WITH).assertIsDisplayed()
        composeTestRule.onNodeWithText(Strings.CONNECT_WITH).performClick()
        assertTrue(clicked)
    }
}
