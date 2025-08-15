package com.example.onboardng_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.SoundRushIcons
import com.example.design_system.theme.SoundRushTheme
import com.example.design_system.theme.mShapes
import com.example.onboardng_screen.screen.Strings

@Composable
fun StartButton(
    onClick: () -> Unit
) {
    Button(
        shape = mShapes.small,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = SoundRushIcons.SoundCloudMulticolored),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = Strings.CONNECT_WITH
            )
        }
    }
}

@Preview
@Composable
fun StartButtonPreview() {
    SoundRushTheme {
        StartButton(
            onClick = {}
        )
    }
}