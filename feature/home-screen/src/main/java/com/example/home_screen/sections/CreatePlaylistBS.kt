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
import com.example.home_screen.screen.HomeScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaylistBS(
    screenState: HomeScreenState,
    onPlaylistNameChange: (String) -> Unit,
    onPlaylistDescriptionChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
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
                onValueChange = { onPlaylistNameChange(it) },
                label = "Playlist name",
                maxLines = 1
            )

            CreatePlaylistBSTextField(
                value = screenState.playlistDescription,
                onValueChange = { onPlaylistDescriptionChange(it) },
                label = "Playlist description",
                maxLines = 2
            )

            Button(
                onClick = onCreateClick,
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