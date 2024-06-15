package com.neocalc.neocalc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue_2,
    onPrimary = Color.White,
    secondary = MediumGray,
    onSecondary = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceContainer = MediumGray,
    error = Color.Red,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue_2,
    onPrimary = Color.White,
    secondary = LinkWhiteDarker,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceContainer = LinkWhite,
    error = Color.Red,
)

@Composable
fun NeoCalcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val extendedColors = getExtendedColors(darkTheme)
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}


@Composable
fun getExtendedColors(darkTheme: Boolean = isSystemInDarkTheme()): ExtendedColors {
    return if (darkTheme)
        ExtendedColors(
            MediumGray,
            Blue_2,
            LightBlack
        )
    else
        ExtendedColors(
            LinkWhiteDarker,
            Color.White,
            LinkWhite.copy(alpha = 0.7f),
        )
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}