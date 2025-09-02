package com.example.home_screen.screen

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

object HomeScreenUtils {
    const val CREATE_PLAYLIST_FAB_TEST_TAG = "create_playlist_fab_test_tag"
}

@Composable
fun CreateVibration(
    distance: Float,
    didVibrate: Boolean,
    viewModel: HomeScreenVM,
) {
    val activity = LocalContext.current as Activity
    LaunchedEffect(distance) {
        if (distance >= 1.5f && !didVibrate) {
            viewModel.sendIntent(HomeScreenIntent.ChangeDidVibrate(true))
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else if (distance < 1f) {
            viewModel.sendIntent(HomeScreenIntent.ChangeDidVibrate(false))
        }
    }
}