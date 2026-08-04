package br.com.vinilapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DefaultLightColors = VinilColors(
    background = Color(0xFFFCFBF8),
    onBackground = Color(0xFF1E1B16),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E1B16),
    surfaceVariant = Color(0xFFE9E1D5),
    onSurfaceVariant = Color(0xFF4A443B),
    primary = Color(0xFF795100),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF695D46),
    onSecondary = Color(0xFFFFFFFF),
    outline = Color(0xFF7B7265),
    transparent = Color.Transparent
)

private val DefaultDarkColors = VinilColors(
    background = Color(0xFF17130E),
    onBackground = Color(0xFFECE1D2),
    surface = Color(0xFF211C16),
    onSurface = Color(0xFFECE1D2),
    surfaceVariant = Color(0xFF4A443B),
    onSurfaceVariant = Color(0xFFCEC5B8),
    primary = Color(0xFFFFBB3D),
    onPrimary = Color(0xFF402D00),
    secondary = Color(0xFFD4C4A3),
    onSecondary = Color(0xFF382F1D),
    outline = Color(0xFF978D7F),
    transparent = Color.Transparent
)

private val DefaultTypography = Typography()

private val DefaultMaterialShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(8.dp),
    extraLarge = RoundedCornerShape(8.dp)
)

private val LocalVinilThemeTokens = staticCompositionLocalOf {
    defaultThemeTokens(isDark = false)
}

object VinilTheme {
    val colors: VinilColors
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.colors

    val fonts: VinilFonts
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.fonts

    val sizes: VinilSizes
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.sizes

    val animations: VinilAnimations
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.animations

    val discs: VinilDiscs
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.discs

    val backgrounds: VinilBackgrounds
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.backgrounds

    val controls: VinilControls
        @Composable
        @ReadOnlyComposable
        get() = LocalVinilThemeTokens.current.controls
}

@Composable
fun VinilAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    tokens: VinilThemeTokens = defaultThemeTokens(isDark = darkTheme),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalVinilThemeTokens provides tokens) {
        MaterialTheme(
            colorScheme = tokens.materialColorScheme,
            typography = tokens.materialTypography,
            shapes = tokens.materialShapes,
            content = content
        )
    }
}

private fun defaultThemeTokens(isDark: Boolean): VinilThemeTokens {
    val colors = if (isDark) DefaultDarkColors else DefaultLightColors

    return VinilThemeTokens(
        colors = colors,
        fonts = defaultFonts(colors),
        sizes = defaultSizes(),
        animations = VinilAnimations(
            contentPlacement = defaultContentPlacementSpec(),
            controls = defaultControlSpec(),
            discRotationDurationMillis = 16_000
        ),
        discs = defaultDiscs(colors),
        backgrounds = defaultBackgrounds(colors),
        controls = defaultControls(colors),
        materialColorScheme = defaultMaterialColorScheme(colors, isDark),
        materialTypography = DefaultTypography,
        materialShapes = DefaultMaterialShapes
    )
}

private fun defaultFonts(colors: VinilColors): VinilFonts = VinilFonts(
    default = FontFamily.Default,
    title = DefaultTypography.headlineSmall.copy(
        color = colors.onBackground,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    headline = DefaultTypography.headlineMedium.copy(
        color = colors.onBackground,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    body = DefaultTypography.bodyLarge.copy(
        color = colors.onSurfaceVariant,
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    caption = DefaultTypography.labelLarge.copy(
        color = colors.onSurfaceVariant,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    control = DefaultTypography.titleMedium.copy(
        color = colors.onPrimary,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    )
)

private fun defaultSizes(): VinilSizes = VinilSizes(
    screenPadding = 24.dp,
    contentSpacing = 16.dp,
    compactSpacing = 8.dp,
    appBarTitleInset = 0.dp,
    controlCornerRadius = 8.dp,
    controlsSpacing = 12.dp,
    discMinSize = 160.dp,
    discMaxSize = 560.dp,
    progressHeight = 8.dp,
    progressThumbSize = 14.dp,
    recordAreaWeight = 2f,
    controlsAreaWeight = 1f,
    secondaryControlWeight = 1f,
    primaryControlWeight = 1.2f
)

private fun defaultDiscs(colors: VinilColors): VinilDiscs = VinilDiscs(
    minSize = 160.dp,
    maxSize = 560.dp,
    baseColor = colors.onBackground,
    grooveColor = colors.outline,
    grooveStrokeWidth = 1.dp,
    grooveAlpha = 0.34f,
    labelColor = colors.primary,
    centerHoleColor = colors.background,
    artworkBackground = Brush.verticalGradient(
        colors = listOf(colors.secondary, colors.primary)
    ),
    artworkSizeFraction = 0.42f,
    labelSizeFraction = 0.58f,
    centerHoleSizeFraction = 0.08f,
    grooveCount = 8
)

private fun defaultBackgrounds(colors: VinilColors): VinilBackgrounds = VinilBackgrounds(
    app = Brush.verticalGradient(
        colors = listOf(colors.background, colors.surface)
    ),
    surface = Brush.verticalGradient(
        colors = listOf(colors.surface, colors.surfaceVariant)
    )
)

private fun defaultControls(colors: VinilColors): VinilControls = VinilControls(
    containerColor = colors.primary,
    contentColor = colors.onPrimary,
    secondaryContainerColor = colors.surfaceVariant,
    secondaryContentColor = colors.onSurfaceVariant,
    disabledContainerColor = colors.surfaceVariant,
    disabledContentColor = colors.onSurfaceVariant,
    cornerShape = RoundedCornerShape(8.dp),
    minTouchTarget = 48.dp,
    primaryTouchTarget = 64.dp,
    progressTrackColor = colors.surfaceVariant,
    progressActiveTrackColor = colors.primary,
    progressThumbColor = colors.primary,
    iconFontSize = 22.sp
)

private fun defaultMaterialColorScheme(colors: VinilColors, isDark: Boolean) = if (isDark) {
    darkColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        secondary = colors.secondary,
        onSecondary = colors.onSecondary,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.surface,
        onSurface = colors.onSurface,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.onSurfaceVariant,
        outline = colors.outline
    )
} else {
    lightColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        secondary = colors.secondary,
        onSecondary = colors.onSecondary,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.surface,
        onSurface = colors.onSurface,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.onSurfaceVariant,
        outline = colors.outline
    )
}
