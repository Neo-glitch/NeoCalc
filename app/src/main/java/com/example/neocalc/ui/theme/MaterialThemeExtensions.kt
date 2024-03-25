package com.example.neocalc.ui.theme


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtendedColors(
//    val primary: Color,
//    val onPrimary: Color,
//    val tertiary: Color,
//    val surface: Color,
//    val onSurface: Color,
//    val error : Color,
    val linkWhiteMediumDarkGrey: Color,
    val whiteBlue: Color,
    val linkWhiteLightBlack: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
//        primary = Color.Unspecified,
//        onPrimary = Color.Unspecified,
//        tertiary = Color.Unspecified,
//        surface = Color.Unspecified,
//        onSurface = Color.Unspecified,
//        error = Color.Unspecified,
        linkWhiteMediumDarkGrey = Color.Unspecified,
        whiteBlue = Color.Unspecified,
        linkWhiteLightBlack = Color.Unspecified
    )
}