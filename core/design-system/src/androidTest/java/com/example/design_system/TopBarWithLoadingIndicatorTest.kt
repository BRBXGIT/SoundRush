package com.example.design_system

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.bars.TopBarWithLoadingIndicator
import com.example.design_system.bars.TopBarWithLoadingIndicatorConstants
import com.example.design_system.theme.SoundRushTheme
import org.junit.Rule
import org.junit.Test

class TopBarWithLoadingIndicatorTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun homeScreenTopBar_show_playlists_text_and_progress_bar() {
        val title = "Some title"

        composeTestRule.setContent {
            SoundRushTheme {
                TopBarWithLoadingIndicator(
                    isLoading = true,
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                    title = title
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TopBarWithLoadingIndicatorConstants.PROGRESS_BAR_TEST_TEG).assertIsDisplayed()
    }
}