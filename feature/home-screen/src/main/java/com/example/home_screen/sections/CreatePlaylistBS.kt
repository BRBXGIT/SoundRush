package com.example.home_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.UiConstants
import com.example.design_system.theme.mShapes
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM

object CreatePlaylistBSConstants {
    const val PLAYLIST_NAME_STRING = "Playlist name"
    const val PLAYLIST_DESCRIPTION_STRING = "Playlist description"
    const val CREATE_PLAYLIST_STRING = "Create playlist"
    const val CREATE_BUTTON_TEST_TAG = "CreateButtonTestTag"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaylistBS(
    screenState: HomeScreenState,
    viewModel: HomeScreenVM,
) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility) },
        shape = mShapes.small,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = UiConstants.HORIZONTAL_PADDING.dp)
                .fillMaxWidth()
        ) {
            CreatePlaylistBSTextField(
                value = screenState.playlistName,
                onValueChange = { viewModel.sendIntent(HomeScreenIntent.ChangePlaylistName(it)) },
                label = CreatePlaylistBSConstants.PLAYLIST_NAME_STRING,
                maxLines = 1
            )

            CreatePlaylistBSTextField(
                value = screenState.playlistDescription,
                onValueChange = { viewModel.sendIntent(HomeScreenIntent.ChangePlaylistDescription(it)) },
                label = CreatePlaylistBSConstants.PLAYLIST_DESCRIPTION_STRING,
                maxLines = 2
            )

            Button(
                onClick = { viewModel.sendIntent(HomeScreenIntent.CreatePlaylist) },
                shape = mShapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(CreatePlaylistBSConstants.CREATE_BUTTON_TEST_TAG),
            ) {
                Text(
                    text = CreatePlaylistBSConstants.CREATE_PLAYLIST_STRING
                )
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@Composable
private fun CreatePlaylistBSTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label
            )
        },
        modifier = Modifier.fillMaxWidth(),
        maxLines = maxLines
    )
}

@Preview
@Composable
private fun CreatePlaylistBSTextFieldPreview() {
    SoundRushTheme {
        CreatePlaylistBSTextField(
            value = "value",
            onValueChange = {},
            label = "label",
            maxLines = 1
        )
    }
}