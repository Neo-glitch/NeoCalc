package com.neocalc.neocalc.calculation.presentation.component

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isFinite
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue
import kotlin.math.ceil


@Deprecated("previously used simple solution to autoresize text, has some bugs")
@Composable
fun AutoResizedText(
	text: String,
	modifier: Modifier = Modifier,
	textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
	color : Color = textStyle.color,
	fontSize: TextUnit = textStyle.fontSize,
	fontStyle: FontStyle? = null,
	fontWeight: FontWeight? = null,
	fontFamily: FontFamily? = null,
	letterSpacing: TextUnit = TextUnit.Unspecified,
	textDecoration: TextDecoration? = null,
	textAlign: TextAlign = TextAlign.End,
	lineHeight: TextUnit = TextUnit.Unspecified,
	maxLines: Int = 1,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	minFontSize: TextUnit,
	scaleFactor: Float = 0.95f
) {

	val alignment: Alignment = when (textAlign) {
		TextAlign.Left -> Alignment.TopStart
		TextAlign.Right -> Alignment.TopEnd
		TextAlign.Center -> Alignment.Center
		TextAlign.Justify -> Alignment.TopCenter
		TextAlign.Start -> Alignment.TopStart
		TextAlign.End -> Alignment.TopEnd
		else -> Alignment.TopStart
	}

	BoxWithConstraints(modifier, contentAlignment = alignment) {
		var shrunkFontSize = fontSize
		val paragraphCalculator = @Composable {
			val mergedStyle = textStyle.merge(
				TextStyle(
					color = color,
					fontSize = shrunkFontSize,
					fontWeight = fontWeight,
					textAlign = textAlign,
					lineHeight = lineHeight,
					fontFamily = fontFamily,
					textDecoration = textDecoration,
					fontStyle = fontStyle,
					letterSpacing = letterSpacing
				)
			)

			// text with given constraints
			Paragraph(
				text = text,
				style = mergedStyle,
				density = LocalDensity.current,
				fontFamilyResolver = LocalFontFamilyResolver.current,
				spanStyles = listOf(),
				placeholders = listOf(),
				maxLines = maxLines,
				ellipsis = false,
				constraints = Constraints(maxWidth = ceil(LocalDensity.current.run { maxWidth.toPx() }).toInt()),
			)
		}

		var intrinsics = paragraphCalculator()
		with(LocalDensity.current) {
			while (
				(intrinsics.didExceedMaxLines || intrinsics.height.toDp() > maxHeight || intrinsics.width.toDp() > maxWidth) &&
				shrunkFontSize >= minFontSize
			) {
				// reduces font size and the assign new values to intrinsic to continue scale process
				shrunkFontSize *= scaleFactor
				intrinsics = paragraphCalculator()
			}
		}

		Text(
			text = text,
			onTextLayout = onTextLayout,
			maxLines = maxLines,
			style = textStyle.merge(
				color = color,
				fontSize = shrunkFontSize,
				fontStyle = fontStyle,
				fontWeight = fontWeight,
				fontFamily = fontFamily,
				textDecoration = textDecoration,
				textAlign = textAlign,
//				lineHeight = with(LocalDensity.current) {
//					(shrunkFontSize.value + 4.sp.value).sp
//				}
			)
		)
	}

}