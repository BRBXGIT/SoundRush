package com.example.home_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.design_system.theme.SoundRushTheme
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM
import com.example.home_screen.sections.CreatePlaylistBS
import com.example.home_screen.sections.CreatePlaylistBSConstants
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class CreatePlaylistBSTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val vm: HomeScreenVM = mockk(relaxed = true)

    @Test
    fun bottomSheet_show_strings_and_send_intent_to_vm() {
        composeTestRule.setContent {
            SoundRushTheme {
                CreatePlaylistBS(
                    screenState = HomeScreenState(isCreatePlaylistBSVisible = true),
                    viewModel = vm
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(CreatePlaylistBSConstants.CREATE_PLAYLIST_STRING).assertIsDisplayed()
        composeTestRule.onNodeWithText(CreatePlaylistBSConstants.PLAYLIST_DESCRIPTION_STRING).assertIsDisplayed()
        composeTestRule.onNodeWithText(CreatePlaylistBSConstants.PLAYLIST_NAME_STRING).assertIsDisplayed()

        composeTestRule.onNodeWithTag(CreatePlaylistBSConstants.CREATE_BUTTON_TEST_TAG).performClick()
        verify { vm.sendIntent(HomeScreenIntent.CreatePlaylist) }
    }
}