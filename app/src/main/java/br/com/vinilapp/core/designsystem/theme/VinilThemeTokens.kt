package br.com.vinilapp.core.designsystem.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Immutable
data class VinilThemeTokens(
    val colors: VinilColors,
    val fonts: VinilFonts,
    val sizes: VinilSizes,
    val animations: VinilAnimations,
    val discs: VinilDiscs,
    val backgrounds: VinilBackgrounds,
    val controls: VinilControls,
    val materialColorScheme: ColorScheme,
    val materialTypography: Typography,
    val materialShapes: Shapes
)

@Immutable
data class VinilColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val outline: Color,
    val transparent: Color
)

@Immutable
data class VinilFonts(
    val default: FontFamily,
    val title: TextStyle,
    val headline: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
    val control: TextStyle
)

@Immutable
data class VinilSizes(
    val screenPadding: Dp,
    val contentSpacing: Dp,
    val compactSpacing: Dp,
    val appBarTitleInset: Dp,
    val controlCornerRadius: Dp,
    val controlsSpacing: Dp,
    val discMinSize: Dp,
    val discMaxSize: Dp,
    val progressHeight: Dp,
    val progressThumbSize: Dp,
    val recordAreaWeight: Float,
    val controlsAreaWeight: Float,
    val secondaryControlWeight: Float,
    val primaryControlWeight: Float
)

@Immutable
data class VinilAnimations(
    val contentPlacement: AnimationSpec<Dp>,
    val controls: AnimationSpec<Float>,
    val discRotationDurationMillis: Int
)

@Immutable
data class VinilDiscs(
    val minSize: Dp,
    val maxSize: Dp,
    val baseColor: Color,
    val grooveColor: Color,
    val grooveStrokeWidth: Dp,
    val grooveAlpha: Float,
    val labelColor: Color,
    val centerHoleColor: Color,
    val artworkBackground: Brush,
    val artworkSizeFraction: Float,
    val labelSizeFraction: Float,
    val centerHoleSizeFraction: Float,
    val grooveCount: Int
)

@Immutable
data class VinilBackgrounds(
    val app: Brush,
    val surface: Brush
)

@Immutable
data class VinilControls(
    val containerColor: Color,
    val contentColor: Color,
    val secondaryContainerColor: Color,
    val secondaryContentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val cornerShape: RoundedCornerShape,
    val minTouchTarget: Dp,
    val primaryTouchTarget: Dp,
    val progressTrackColor: Color,
    val progressActiveTrackColor: Color,
    val progressThumbColor: Color,
    val iconFontSize: TextUnit
)

internal fun defaultContentPlacementSpec(): AnimationSpec<Dp> = spring(stiffness = Spring.StiffnessMediumLow)

internal fun defaultControlSpec(): AnimationSpec<Float> = spring(stiffness = Spring.StiffnessMedium)
