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
    val body: TextStyle
)

@Immutable
data class VinilSizes(
    val screenPadding: Dp,
    val contentSpacing: Dp,
    val appBarTitleInset: Dp,
    val controlCornerRadius: Dp,
    val discMinSize: Dp,
    val discMaxSize: Dp
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
    val labelColor: Color
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
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val cornerShape: RoundedCornerShape,
    val minTouchTarget: Dp
)

internal fun defaultContentPlacementSpec(): AnimationSpec<Dp> = spring(stiffness = Spring.StiffnessMediumLow)

internal fun defaultControlSpec(): AnimationSpec<Float> = spring(stiffness = Spring.StiffnessMedium)
