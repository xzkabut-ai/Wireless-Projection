package com.wireless.projection.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = ProjectionBlue,
    onPrimary = ProjectionBackground,
    secondary = ProjectionBlueDark,
    background = ProjectionBackground,
    surface = ProjectionBackground,
    surfaceVariant = ProjectionSurfaceVariant,
)

@Composable
fun WirelessProjectionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = ProjectionTypography,
        content = content,
    )
}
