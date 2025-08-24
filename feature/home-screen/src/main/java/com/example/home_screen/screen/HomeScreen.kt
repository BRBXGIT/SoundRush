package com.example.home_screen.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.design_system.theme.mColors

@Composable
fun HomeScreen(
    navController: NavController,
    commonVM: CommonVM
) {
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

    LaunchedEffect(commonState) {
        Log.d("CCCC", commonState.toString())
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            Button(
                onClick = { commonVM.sendIntent(CommonIntent.RefreshTokens) }
            ) {
                Text(
                    text = "Refresh"
                )
            }
        }
    }
}