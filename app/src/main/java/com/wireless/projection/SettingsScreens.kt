package com.wireless.projection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wireless.projection.ui.theme.ProjectionBlue
import com.wireless.projection.ui.theme.ProjectionMuted
import com.wireless.projection.ui.theme.ProjectionNavy
import com.wireless.projection.ui.theme.ProjectionSurface
import com.wireless.projection.ui.theme.ProjectionSurfaceRaised
import com.wireless.projection.ui.theme.ProjectionText

@Composable
fun SettingsScreen(
    appVersion: String,
    onBack: () -> Unit,
    onAbout: () -> Unit,
    onHelp: () -> Unit,
    onPrivacy: () -> Unit,
    onSystemSettings: () -> Unit,
) {
    SettingsPage(
        title = stringResource(R.string.settings),
        onBack = onBack,
    ) {
        Text(
            text = stringResource(R.string.settings_section_app),
            color = ProjectionMuted,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        SettingsOption(
            title = stringResource(R.string.about_application),
            subtitle = stringResource(R.string.about_application_summary),
            icon = Icons.Filled.Info,
            onClick = onAbout,
        )
        Spacer(modifier = Modifier.height(10.dp))
        VersionCard(appVersion)

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.settings_section_support),
            color = ProjectionMuted,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        SettingsOption(
            title = stringResource(R.string.help),
            subtitle = stringResource(R.string.help_summary),
            icon = Icons.AutoMirrored.Filled.Help,
            onClick = onHelp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        SettingsOption(
            title = stringResource(R.string.privacy),
            subtitle = stringResource(R.string.privacy_summary),
            icon = Icons.Filled.Lock,
            onClick = onPrivacy,
        )
        Spacer(modifier = Modifier.height(24.dp))
        SettingsOption(
            title = stringResource(R.string.android_settings),
            subtitle = stringResource(R.string.android_settings_summary),
            icon = Icons.Filled.Settings,
            onClick = onSystemSettings,
        )
    }
}

@Composable
fun AboutScreen(appVersion: String, onBack: () -> Unit) {
    SettingsPage(
        title = stringResource(R.string.about_application),
        onBack = onBack,
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(78.dp),
            shape = RoundedCornerShape(24.dp),
            color = ProjectionBlue.copy(alpha = 0.16f),
        ) {
            Icon(
                imageVector = Icons.Filled.Tv,
                contentDescription = stringResource(R.string.tv_icon_description),
                tint = ProjectionBlue,
                modifier = Modifier.padding(19.dp),
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.app_name),
            color = ProjectionText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = stringResource(R.string.version_value, appVersion),
            color = ProjectionMuted,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 6.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        InformationCard(text = stringResource(R.string.about_application_details))
    }
}

@Composable
fun HelpScreen(onBack: () -> Unit, onConnect: () -> Unit) {
    SettingsPage(
        title = stringResource(R.string.help),
        onBack = onBack,
    ) {
        Text(
            text = stringResource(R.string.help_intro),
            color = ProjectionMuted,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HelpStep(1, stringResource(R.string.connection_guide_wifi))
        HelpStep(2, stringResource(R.string.connection_guide_tv))
        HelpStep(3, stringResource(R.string.connection_guide_select))
        Spacer(modifier = Modifier.height(16.dp))
        InformationCard(text = stringResource(R.string.help_limitation))
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onConnect,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ProjectionBlue,
                contentColor = ProjectionNavy,
            ),
        ) {
            Text(
                text = stringResource(R.string.open_cast_settings),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    SettingsPage(
        title = stringResource(R.string.privacy),
        onBack = onBack,
    ) {
        InformationCard(text = stringResource(R.string.privacy_details))
        Spacer(modifier = Modifier.height(12.dp))
        InformationCard(text = stringResource(R.string.privacy_display_data))
    }
}

@Composable
private fun SettingsPage(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(containerColor = ProjectionNavy) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 22.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(44.dp)
                        .background(ProjectionSurfaceRaised, CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = ProjectionText,
                    )
                }
                Text(
                    text = title,
                    color = ProjectionText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 14.dp),
                )
            }
            content()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsOption(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ProjectionSurface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(42.dp),
                shape = RoundedCornerShape(14.dp),
                color = ProjectionBlue.copy(alpha = 0.15f),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ProjectionBlue,
                    modifier = Modifier.padding(11.dp),
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = title,
                    color = ProjectionText,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = subtitle,
                    color = ProjectionMuted,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 3.dp),
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = ProjectionMuted,
            )
        }
    }
}

@Composable
private fun VersionCard(appVersion: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ProjectionSurface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.version),
                color = ProjectionText,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = appVersion,
                color = ProjectionMuted,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun InformationCard(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ProjectionSurface),
    ) {
        Text(
            text = text,
            color = ProjectionMuted,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(18.dp),
        )
    }
}

@Composable
private fun HelpStep(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            color = ProjectionBlue.copy(alpha = 0.16f),
        ) {
            androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number.toString(),
                    color = ProjectionBlue,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Text(
            text = text,
            color = ProjectionText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}
