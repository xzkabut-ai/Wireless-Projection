package com.wireless.projection.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = ProjectionBlue,
    onPrimary = ProjectionNavy,
    secondary = ProjectionBlueDark,
    background = ProjectionNavy,
    onBackground = ProjectionText,
    surface = ProjectionSurface,
    onSurface = ProjectionText,
    surfaceVariant = ProjectionSurfaceRaised,
    onSurfaceVariant = ProjectionMuted,
    outline = ProjectionBorder,
)

@Composable
fun WirelessProjectionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = ProjectionTypography,
        content = content,
    )
}
