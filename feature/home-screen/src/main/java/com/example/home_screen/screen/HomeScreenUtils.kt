package com.example.home_screen.screen

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
    val context = LocalContext.current
    val vibrator = remember(context) { context.getVibrator() }

    LaunchedEffect(distance) {
        when {
            distance >= 1.5f && !didVibrate -> {
                viewModel.sendIntent(HomeScreenIntent.ChangeDidVibrate(true))
                vibrator?.vibrateOnce()
            }
            distance < 1f && didVibrate -> {
                viewModel.sendIntent(HomeScreenIntent.ChangeDidVibrate(false))
            }
        }
    }
}

@Suppress("DEPRECATION")
private fun Context.getVibrator(): Vibrator? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val manager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        manager?.defaultVibrator
    } else {
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
}

@Suppress("DEPRECATION")
private fun Vibrator.vibrateOnce(duration: Long = 50L) {
    val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
    vibrate(effect)
}