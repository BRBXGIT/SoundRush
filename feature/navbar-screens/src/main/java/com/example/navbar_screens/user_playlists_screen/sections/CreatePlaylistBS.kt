package com.example.navbar_screens.user_playlists_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonDimens
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenState
import com.example.navbar_screens.user_playlists_screen.screen.UserPlaylistsScreenUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaylistBS(
    screenState: UserPlaylistsScreenState,
    onDismissRequest: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCreatePlaylistClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UserPlaylistsScreenUtils.BS_COLUMN_SPACER.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = CommonDimens.HORIZONTAL_PADDING.dp)
        ) {
            Text(
                text = UserPlaylistsScreenUtils.CREATE_PLAYLIST_TEXT,
                style = mTypography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = screenState.newPlaylistTitle,
                onValueChange = { onTitleChange(it) },
                label = {
                    Text(
                        text = UserPlaylistsScreenUtils.CREATE_PLAYLIST_TITLE_TF_LABEL
                    )
                }
            )

            OutlinedTextField(
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                value = screenState.newPlaylistDescription,
                onValueChange = { onDescriptionChange(it) },
                label = {
                    Text(
                        text = UserPlaylistsScreenUtils.CREATE_PLAYLIST_DESCRIPTION_TF_LABEL
                    )
                }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = mShapes.small,
                onClick = onCreatePlaylistClick
            ) {
                Text(
                    text = UserPlaylistsScreenUtils.CREATE_PLAYLIST_BTN_TEXT
                )
            }
        }
    }
}