package com.neocalc.neocalc.history.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LazyListScope.ItemDivider(
	modifier: Modifier = Modifier,
	dividerColor: Color = Color.DarkGray,
	dividerHeight: Dp = 2.dp
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.height(dividerHeight)
			.background(dividerColor)
	)
}