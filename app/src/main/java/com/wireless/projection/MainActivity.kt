package com.wireless.projection

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wireless.projection.ui.theme.WirelessProjectionTheme

class MainActivity : ComponentActivity() {
    private var wirelessDisplaySettingsAvailable by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WirelessProjectionTheme {
                ProjectionScreen(
                    wirelessDisplaySettingsAvailable = wirelessDisplaySettingsAvailable,
                    onConnect = ::openWirelessDisplaySettings,
                    onSettings = ::openSystemSettings,
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        wirelessDisplaySettingsAvailable = wirelessDisplaySettingsIntent() != null
    }

    private fun openWirelessDisplaySettings() {
        val intent = wirelessDisplaySettingsIntent()
        if (intent == null) {
            showMessage(getString(R.string.wireless_display_unavailable))
            return
        }

        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            showMessage(getString(R.string.wireless_display_settings_unavailable))
        } catch (_: SecurityException) {
            showMessage(getString(R.string.wireless_display_settings_unavailable))
        }
    }

    private fun openSystemSettings() {
        try {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        } catch (_: ActivityNotFoundException) {
            showMessage(getString(R.string.system_settings_unavailable))
        } catch (_: SecurityException) {
            showMessage(getString(R.string.system_settings_unavailable))
        }
    }

    private fun wirelessDisplaySettingsIntent(): Intent? {
        val actions = listOf(
            ACTION_WIFI_DISPLAY_SETTINGS,
            Settings.ACTION_CAST_SETTINGS,
        )
        return actions
            .map { action -> Intent(action) }
            .firstOrNull { it.resolveActivity(packageManager) != null }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private companion object {
        const val ACTION_WIFI_DISPLAY_SETTINGS = "android.settings.WIFI_DISPLAY_SETTINGS"
    }
}
