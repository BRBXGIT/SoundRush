package com.example.home_screen

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.sections.HomeScreenTopBar
import com.example.home_screen.sections.HomeScreenTopBarConstants
import org.junit.Rule
import org.junit.Test

class HomeScreenTopbarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun homeScreenTopBarShowPlaylistsTest() {
        composeTestRule.setContent {
            SoundRushTheme {
                HomeScreenTopBar(
                    isLoading = true,
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(HomeScreenTopBarConstants.PLAYLISTS_TEST_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeScreenTopBarConstants.PROGRESS_BAR_TEST_TEG).assertIsDisplayed()
    }
}