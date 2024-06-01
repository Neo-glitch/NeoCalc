package com.neocalc.neocalc.calculation.presentation.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import kotlin.math.ceil

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
	maxLines: Int = Int.MAX_VALUE,
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

		var intrinsinc = paragraphCalculator()
		with(LocalDensity.current) {
			while (
				(intrinsinc.didExceedMaxLines || intrinsinc.width.toDp() > maxWidth || intrinsinc.height.toDp() > maxHeight) &&
				shrunkFontSize > minFontSize
			) {
				// reduces font size and the assign new values to intrinsic to continue scale process
				shrunkFontSize *= scaleFactor
				intrinsinc = paragraphCalculator()
			}
		}

		Text(
			text = text,
			color = color,
			fontSize = shrunkFontSize,
			fontStyle = fontStyle,
			fontWeight = fontWeight,
			fontFamily = fontFamily,
			letterSpacing = letterSpacing,
			textDecoration = textDecoration,
			textAlign = textAlign,
			lineHeight = lineHeight,
			onTextLayout = onTextLayout,
			maxLines = maxLines,
			style = textStyle
		)
	}
}