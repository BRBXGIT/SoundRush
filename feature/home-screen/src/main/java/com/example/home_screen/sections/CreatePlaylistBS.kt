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
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.UiConstants
import com.example.design_system.theme.mShapes
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM

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
                label = "Playlist name",
                maxLines = 1
            )

            CreatePlaylistBSTextField(
                value = screenState.playlistDescription,
                onValueChange = { viewModel.sendIntent(HomeScreenIntent.ChangePlaylistDescription(it)) },
                label = "Playlist description",
                maxLines = 2
            )

            Button(
                onClick = { viewModel.sendIntent(HomeScreenIntent.CreatePlaylist) },
                modifier = Modifier.fillMaxWidth(),
                shape = mShapes.small
            ) {
                Text(
                    text = "Create playlist"
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