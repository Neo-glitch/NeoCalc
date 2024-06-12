package com.neocalc.neocalc.history.presentation.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.hasReachedBottom(buffer: Int = 1): Boolean {
	val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
	return lastVisibleItem?.index != 0 && (lastVisibleItem?.index == this.layoutInfo.totalItemsCount - 3)
}