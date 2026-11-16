package com.wireless.projection

import android.content.ActivityNotFoundException
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    private var presentationDisplays by mutableStateOf(emptyList<PresentationDisplay>())
    private var displayStatusAvailable by mutableStateOf(false)
    private var displayManager: DisplayManager? = null

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = refreshPresentationDisplays()

        override fun onDisplayChanged(displayId: Int) = refreshPresentationDisplays()

        override fun onDisplayRemoved(displayId: Int) = refreshPresentationDisplays()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WirelessProjectionTheme {
                WirelessProjectionApp(
                    wirelessDisplaySettingsAvailable = wirelessDisplaySettingsAvailable,
                    presentationDisplays = presentationDisplays,
                    displayStatusAvailable = displayStatusAvailable,
                    onConnect = ::openWirelessDisplaySettings,
                    onSystemSettings = ::openSystemSettings,
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val manager = getSystemService(DisplayManager::class.java)
        if (manager == null) {
            presentationDisplays = emptyList()
            displayStatusAvailable = false
            showMessage(getString(R.string.display_status_unavailable))
            return
        }

        displayManager = manager
        manager.registerDisplayListener(displayListener, Handler(Looper.getMainLooper()))
        displayStatusAvailable = true
        refreshPresentationDisplays()
    }

    override fun onResume() {
        super.onResume()
        wirelessDisplaySettingsAvailable = wirelessDisplaySettingsIntent() != null
    }

    override fun onStop() {
        displayManager?.unregisterDisplayListener(displayListener)
        displayManager = null
        super.onStop()
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

    private fun refreshPresentationDisplays() {
        val manager = displayManager ?: return
        presentationDisplays = manager
            .getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
            .map { display -> PresentationDisplay(display.displayId, display.name) }
            .sortedBy { display -> display.name.lowercase() }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private companion object {
        const val ACTION_WIFI_DISPLAY_SETTINGS = "android.settings.WIFI_DISPLAY_SETTINGS"
    }
}
