package com.neocalc.neocalc.ui.theme


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtendedColors(
    val linkWhiteMediumDarkGrey: Color,
    val whiteBlue: Color,
    val linkWhiteLightBlack: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        linkWhiteMediumDarkGrey = Color.Unspecified,
        whiteBlue = Color.Unspecified,
        linkWhiteLightBlack = Color.Unspecified
    )
}