package com.wireless.projection

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

private enum class AppPage {
    HOME,
    SETTINGS,
    ABOUT,
    HELP,
    PRIVACY,
}

@Composable
fun WirelessProjectionApp(
    wirelessDisplaySettingsAvailable: Boolean,
    presentationDisplays: List<PresentationDisplay>,
    displayStatusAvailable: Boolean,
    onConnect: () -> Unit,
    onSystemSettings: () -> Unit,
) {
    var currentPage by rememberSaveable { mutableStateOf(AppPage.HOME) }

    BackHandler(enabled = currentPage != AppPage.HOME) {
        currentPage = if (currentPage == AppPage.SETTINGS) {
            AppPage.HOME
        } else {
            AppPage.SETTINGS
        }
    }

    when (currentPage) {
        AppPage.HOME -> ProjectionScreen(
            wirelessDisplaySettingsAvailable = wirelessDisplaySettingsAvailable,
            presentationDisplays = presentationDisplays,
            displayStatusAvailable = displayStatusAvailable,
            onConnect = onConnect,
            onSettings = { currentPage = AppPage.SETTINGS },
        )

        AppPage.SETTINGS -> SettingsScreen(
            appVersion = BuildConfig.VERSION_NAME,
            onBack = { currentPage = AppPage.HOME },
            onAbout = { currentPage = AppPage.ABOUT },
            onHelp = { currentPage = AppPage.HELP },
            onPrivacy = { currentPage = AppPage.PRIVACY },
            onSystemSettings = onSystemSettings,
        )

        AppPage.ABOUT -> AboutScreen(
            appVersion = BuildConfig.VERSION_NAME,
            onBack = { currentPage = AppPage.SETTINGS },
        )

        AppPage.HELP -> HelpScreen(
            onBack = { currentPage = AppPage.SETTINGS },
            onConnect = onConnect,
        )

        AppPage.PRIVACY -> PrivacyScreen(
            onBack = { currentPage = AppPage.SETTINGS },
        )
    }
}
