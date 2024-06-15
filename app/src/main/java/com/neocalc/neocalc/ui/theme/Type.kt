package com.neocalc.neocalc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    // body variation is used for content bodies (avoid expressive fonts)
    // LineHeight is 1.5 times the font size
    bodyLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),

    // does same role as headline but used for titles but smaller than headline (careful when using expressive fonts)
    // we can use this for header for a section of items
    // LineHeight is 1.2 times the font size
    titleLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.4.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.8.sp,
    ),

    // display is used for very large display text or prominent heading (allows for expressive fonts)
    // LineHeight is 1.2 times the font size
    displayLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 54.sp,
        lineHeight = 64.8.sp
    ),
    displayMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        lineHeight = 52.8.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 40.8.sp,
    ),

    // for section headings or prominent text (allows expressive fonts)
    // LineHeight is 1.2 times the font size
    headlineLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        lineHeight = 40.8.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 31.2.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 26.4.sp,
    ),

    // for labels, buttons and other auxiliary text
    // LineHeight is 1.5 times the font size
    labelLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 15.sp,
    )
)