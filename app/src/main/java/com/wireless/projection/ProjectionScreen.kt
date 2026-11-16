package com.wireless.projection

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wireless.projection.ui.theme.ProjectionBlue
import com.wireless.projection.ui.theme.ProjectionMuted
import com.wireless.projection.ui.theme.ProjectionNavy
import com.wireless.projection.ui.theme.ProjectionSuccess
import com.wireless.projection.ui.theme.ProjectionSurface
import com.wireless.projection.ui.theme.ProjectionSurfaceRaised
import com.wireless.projection.ui.theme.ProjectionText

@Composable
fun ProjectionScreen(
    wirelessDisplaySettingsAvailable: Boolean,
    presentationDisplays: List<PresentationDisplay>,
    displayStatusAvailable: Boolean,
    onConnect: () -> Unit,
    onSettings: () -> Unit,
) {
    val connected = displayStatusAvailable && presentationDisplays.isNotEmpty()
    val statusText = when {
        !displayStatusAvailable -> stringResource(R.string.connection_status_unavailable)
        connected -> stringResource(R.string.connection_connected)
        else -> stringResource(R.string.connection_disconnected)
    }
    val statusDescription = when {
        !displayStatusAvailable -> stringResource(R.string.display_status_unavailable)
        connected -> stringResource(R.string.connection_connected_explanation)
        else -> stringResource(R.string.connection_disconnected_explanation)
    }

    Scaffold(containerColor = ProjectionNavy) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 22.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = ProjectionBlue.copy(alpha = 0.18f),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Cast,
                        contentDescription = null,
                        tint = ProjectionBlue,
                        modifier = Modifier.padding(11.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = ProjectionText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = stringResource(R.string.header_subtitle),
                        color = ProjectionMuted,
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 1.2.sp,
                    )
                }
                IconButton(
                    onClick = onSettings,
                    modifier = Modifier
                        .size(44.dp)
                        .background(ProjectionSurfaceRaised, CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.settings),
                        tint = ProjectionText,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = ProjectionSurface),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(205.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        ProjectionSurfaceRaised,
                                        ProjectionSurface,
                                        ProjectionNavy,
                                    ),
                                ),
                            ),
                    ) {
                        ConnectedDevicesIllustration(Modifier.fillMaxSize())
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(18.dp),
                            shape = RoundedCornerShape(50),
                            color = ProjectionBlue.copy(alpha = 0.16f),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 11.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Wifi,
                                    contentDescription = null,
                                    tint = ProjectionBlue,
                                    modifier = Modifier.size(14.dp),
                                )
                                Text(
                                    text = stringResource(R.string.system_controlled_badge),
                                    color = ProjectionBlue,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(start = 6.dp),
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp)
                            .padding(bottom = 22.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.home_title),
                            color = ProjectionText,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = stringResource(R.string.home_description),
                            color = ProjectionMuted,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = ProjectionSurface),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(11.dp),
                            shape = CircleShape,
                            color = if (connected) ProjectionSuccess else ProjectionMuted,
                        ) {}
                        Text(
                            text = statusText,
                            color = ProjectionText,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp),
                        )
                        Text(
                            text = stringResource(
                                if (wirelessDisplaySettingsAvailable) {
                                    R.string.system_screen_available
                                } else {
                                    R.string.system_screen_unavailable
                                },
                            ),
                            color = if (wirelessDisplaySettingsAvailable) {
                                ProjectionBlue
                            } else {
                                ProjectionMuted
                            },
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    Text(
                        text = statusDescription,
                        color = ProjectionMuted,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    if (connected) {
                        presentationDisplays.forEach { display ->
                            val name = display.name.takeIf(String::isNotBlank)
                                ?: stringResource(R.string.display_name_fallback, display.id)
                            Text(
                                text = name,
                                color = ProjectionText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 10.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onConnect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(19.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProjectionBlue,
                    contentColor = ProjectionNavy,
                ),
            ) {
                Icon(
                    imageVector = Icons.Filled.Cast,
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                )
                Text(
                    text = stringResource(R.string.connect_tv),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = stringResource(R.string.connection_guide_title),
                color = ProjectionText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            InstructionStep(
                number = 1,
                text = stringResource(R.string.connection_guide_wifi),
            )
            InstructionStep(
                number = 2,
                text = stringResource(R.string.connection_guide_tv),
            )
            InstructionStep(
                number = 3,
                text = stringResource(R.string.connection_guide_select),
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.home_footer),
                color = ProjectionMuted,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp),
            )
        }
    }
}

@Composable
private fun InstructionStep(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(30.dp),
            shape = CircleShape,
            color = ProjectionBlue.copy(alpha = 0.16f),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number.toString(),
                    color = ProjectionBlue,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Text(
            text = text,
            color = ProjectionMuted,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}

@Composable
private fun ConnectedDevicesIllustration(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "device-connection-glow")
    val glowAlpha by transition.animateFloat(
        initialValue = 0.10f,
        targetValue = 0.24f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "device-glow-alpha",
    )

    Canvas(modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        val tvLeft = size.width * 0.12f
        val tvTop = size.height * 0.24f
        val tvWidth = size.width * 0.59f
        val tvHeight = size.height * 0.55f
        val phoneLeft = size.width * 0.66f
        val phoneTop = size.height * 0.12f
        val phoneWidth = size.width * 0.22f
        val phoneHeight = size.height * 0.70f
        val tvCorner = CornerRadius(14.dp.toPx())
        val phoneCorner = CornerRadius(13.dp.toPx())

        drawCircle(
            color = ProjectionBlue.copy(alpha = glowAlpha),
            radius = size.minDimension * 0.34f,
            center = Offset(size.width * 0.56f, size.height * 0.49f),
        )

        val connectionPath = Path().apply {
            moveTo(tvLeft + tvWidth * 0.76f, tvTop + tvHeight * 0.48f)
            cubicTo(
                size.width * 0.70f,
                size.height * 0.28f,
                size.width * 0.68f,
                size.height * 0.72f,
                phoneLeft + phoneWidth * 0.18f,
                phoneTop + phoneHeight * 0.52f,
            )
        }
        drawPath(
            path = connectionPath,
            color = ProjectionBlue.copy(alpha = 0.68f),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round),
        )

        drawRoundRect(
            color = ProjectionSurfaceRaised,
            topLeft = Offset(tvLeft, tvTop),
            size = Size(tvWidth, tvHeight),
            cornerRadius = tvCorner,
        )
        drawRoundRect(
            color = ProjectionBlue.copy(alpha = 0.78f),
            topLeft = Offset(tvLeft, tvTop),
            size = Size(tvWidth, tvHeight),
            cornerRadius = tvCorner,
            style = Stroke(width = 2.dp.toPx()),
        )
        drawRoundRect(
            brush = Brush.linearGradient(
                colors = listOf(ProjectionBlue.copy(alpha = 0.26f), ProjectionNavy),
                start = Offset(tvLeft, tvTop),
                end = Offset(tvLeft + tvWidth, tvTop + tvHeight),
            ),
            topLeft = Offset(tvLeft + 8.dp.toPx(), tvTop + 8.dp.toPx()),
            size = Size(tvWidth - 16.dp.toPx(), tvHeight - 16.dp.toPx()),
            cornerRadius = CornerRadius(8.dp.toPx()),
        )
        drawLine(
            color = ProjectionBlue.copy(alpha = 0.76f),
            start = Offset(tvLeft + tvWidth * 0.50f, tvTop + tvHeight),
            end = Offset(tvLeft + tvWidth * 0.50f, tvTop + tvHeight + 10.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round,
        )
        drawLine(
            color = ProjectionBlue.copy(alpha = 0.76f),
            start = Offset(tvLeft + tvWidth * 0.35f, tvTop + tvHeight + 11.dp.toPx()),
            end = Offset(tvLeft + tvWidth * 0.65f, tvTop + tvHeight + 11.dp.toPx()),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round,
        )

        drawRoundRect(
            color = ProjectionNavy,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = phoneCorner,
        )
        drawRoundRect(
            color = ProjectionBlue,
            topLeft = Offset(phoneLeft, phoneTop),
            size = Size(phoneWidth, phoneHeight),
            cornerRadius = phoneCorner,
            style = Stroke(width = 2.dp.toPx()),
        )
        drawRoundRect(
            color = ProjectionBlue.copy(alpha = 0.22f),
            topLeft = Offset(phoneLeft + 5.dp.toPx(), phoneTop + 13.dp.toPx()),
            size = Size(phoneWidth - 10.dp.toPx(), phoneHeight - 26.dp.toPx()),
            cornerRadius = CornerRadius(7.dp.toPx()),
        )
        drawLine(
            color = ProjectionBlue.copy(alpha = 0.75f),
            start = Offset(phoneLeft + phoneWidth * 0.42f, phoneTop + 7.dp.toPx()),
            end = Offset(phoneLeft + phoneWidth * 0.58f, phoneTop + 7.dp.toPx()),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round,
        )
    }
}
