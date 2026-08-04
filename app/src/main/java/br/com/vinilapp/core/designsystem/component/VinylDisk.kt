package br.com.vinilapp.core.designsystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import br.com.vinilapp.core.designsystem.theme.VinilTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VinylDisk(modifier: Modifier = Modifier, isRotating: Boolean = true, albumArtwork: @Composable () -> Unit) {
    val discs = VinilTheme.discs
    val animations = VinilTheme.animations
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_disk_rotation")
    val animatedRotation by infiniteTransition.animateFloat(
        initialValue = ROTATION_START,
        targetValue = ROTATION_END,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animations.discRotationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "vinyl_disk_angle"
    )
    val rotation = if (isRotating) animatedRotation else ROTATION_START

    Box(
        modifier = modifier
            .shadow(
                elevation = discs.shadowElevation,
                shape = CircleShape,
                ambientColor = discs.shadowColor.copy(alpha = SHADOW_AMBIENT_ALPHA),
                spotColor = discs.shadowColor.copy(alpha = SHADOW_SPOT_ALPHA)
            )
            .clip(CircleShape)
            .graphicsLayer {
                rotationZ = rotation
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / RADIUS_DIVISOR
            val center = this.center
            val grooveStart = radius * discs.grooveStartFraction
            val grooveEnd = radius * discs.grooveEndFraction
            val grooveStep = (grooveEnd - grooveStart) / discs.grooveCount.coerceAtLeast(MIN_GROOVE_COUNT)

            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0f to lerp(discs.baseColor, discs.surfaceTintColor, CENTER_TINT_FRACTION),
                        0.46f to discs.baseColor,
                        0.78f to discs.baseColor,
                        1f to discs.rimShadowColor
                    ),
                    center = center,
                    radius = radius
                )
            )

            drawCircle(
                brush = Brush.sweepGradient(
                    colorStops = arrayOf(
                        0.05f to Color.Transparent,
                        0.16f to discs.highlightColor.copy(alpha = discs.highlightAlpha),
                        0.28f to Color.Transparent,
                        0.58f to discs.shadowColor.copy(alpha = discs.depthShadowAlpha),
                        0.76f to Color.Transparent,
                        1f to Color.Transparent
                    ),
                    center = center
                )
            )

            drawCircle(
                color = discs.rimHighlightColor.copy(alpha = RIM_HIGHLIGHT_ALPHA),
                radius = radius - discs.grooveStrokeWidth.toPx(),
                style = Stroke(width = OUTER_RIM_WIDTH_DP.dp.toPx())
            )
            drawCircle(
                color = discs.rimShadowColor.copy(alpha = RIM_SHADOW_ALPHA),
                radius = radius - OUTER_RIM_WIDTH_DP.dp.toPx(),
                style = Stroke(width = INNER_RIM_WIDTH_DP.dp.toPx())
            )

            repeat(discs.grooveCount) { index ->
                val grooveRadius = grooveStart + grooveStep * index
                val alpha = discs.grooveAlpha * (GROOVE_ALPHA_BASE + GROOVE_ALPHA_VARIATION * (index % 3))
                drawCircle(
                    color = discs.grooveColor.copy(alpha = alpha),
                    radius = grooveRadius,
                    style = Stroke(width = discs.grooveStrokeWidth.toPx())
                )
                if (index % HIGHLIGHTED_GROOVE_INTERVAL == 0) {
                    drawArc(
                        color = discs.rimHighlightColor.copy(alpha = discs.grooveHighlightAlpha),
                        startAngle = GROOVE_HIGHLIGHT_START_ANGLE,
                        sweepAngle = GROOVE_HIGHLIGHT_SWEEP_ANGLE,
                        useCenter = false,
                        topLeft = Offset(center.x - grooveRadius, center.y - grooveRadius),
                        size = Size(grooveRadius * 2f, grooveRadius * 2f),
                        style = Stroke(width = discs.grooveStrokeWidth.toPx())
                    )
                }
            }

            repeat(TEXTURE_MARK_COUNT) { index ->
                val angle = index * TEXTURE_ANGLE_STEP
                val distanceFraction = TEXTURE_INNER_FRACTION +
                    (index * TEXTURE_DISTANCE_MULTIPLIER % TEXTURE_DISTANCE_BUCKETS) /
                    TEXTURE_DISTANCE_DIVISOR
                val markRadius = radius * distanceFraction
                val markCenter = Offset(
                    x = center.x + cos(angle).toFloat() * markRadius,
                    y = center.y + sin(angle).toFloat() * markRadius
                )
                drawCircle(
                    color = discs.textureColor.copy(
                        alpha = discs.textureAlpha * (TEXTURE_ALPHA_BASE + (index % 4) * TEXTURE_ALPHA_STEP)
                    ),
                    radius = radius * TEXTURE_DOT_RADIUS_FRACTION,
                    center = markCenter
                )
            }

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        discs.labelColor,
                        lerp(discs.labelColor, discs.shadowColor, LABEL_EDGE_DARKEN_FRACTION)
                    ),
                    center = center,
                    radius = radius * discs.labelSizeFraction
                ),
                radius = radius * discs.labelSizeFraction
            )
            drawCircle(
                color = discs.highlightColor.copy(alpha = LABEL_HIGHLIGHT_ALPHA),
                radius = radius * discs.labelSizeFraction,
                style = Stroke(width = LABEL_STROKE_WIDTH_DP.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(discs.artworkSizeFraction),
            contentAlignment = Alignment.Center
        ) {
            albumArtwork()
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / RADIUS_DIVISOR

            drawCircle(
                color = discs.centerHoleColor,
                radius = radius * discs.centerHoleSizeFraction
            )
            drawCircle(
                color = discs.centerHoleHighlightColor.copy(alpha = CENTER_HOLE_HIGHLIGHT_ALPHA),
                radius = radius * discs.centerHoleSizeFraction,
                style = Stroke(width = CENTER_HOLE_STROKE_WIDTH_DP.dp.toPx())
            )
        }
    }
}

private const val ROTATION_START = 0f
private const val ROTATION_END = 360f
private const val RADIUS_DIVISOR = 2f
private const val MIN_GROOVE_COUNT = 1
private const val SHADOW_AMBIENT_ALPHA = 0.34f
private const val SHADOW_SPOT_ALPHA = 0.42f
private const val CENTER_TINT_FRACTION = 0.16f
private const val RIM_HIGHLIGHT_ALPHA = 0.42f
private const val RIM_SHADOW_ALPHA = 0.62f
private const val OUTER_RIM_WIDTH_DP = 2
private const val INNER_RIM_WIDTH_DP = 5
private const val GROOVE_ALPHA_BASE = 0.72f
private const val GROOVE_ALPHA_VARIATION = 0.08f
private const val HIGHLIGHTED_GROOVE_INTERVAL = 5
private const val GROOVE_HIGHLIGHT_START_ANGLE = 214f
private const val GROOVE_HIGHLIGHT_SWEEP_ANGLE = 54f
private const val TEXTURE_MARK_COUNT = 150
private const val TEXTURE_ANGLE_STEP = 2.3999631f
private const val TEXTURE_INNER_FRACTION = 0.24f
private const val TEXTURE_DISTANCE_MULTIPLIER = 37
private const val TEXTURE_DISTANCE_BUCKETS = 67
private const val TEXTURE_DISTANCE_DIVISOR = 98f
private const val TEXTURE_ALPHA_BASE = 0.45f
private const val TEXTURE_ALPHA_STEP = 0.08f
private const val TEXTURE_DOT_RADIUS_FRACTION = 0.0028f
private const val LABEL_EDGE_DARKEN_FRACTION = 0.22f
private const val LABEL_HIGHLIGHT_ALPHA = 0.20f
private const val LABEL_STROKE_WIDTH_DP = 1
private const val CENTER_HOLE_HIGHLIGHT_ALPHA = 0.70f
private const val CENTER_HOLE_STROKE_WIDTH_DP = 1
