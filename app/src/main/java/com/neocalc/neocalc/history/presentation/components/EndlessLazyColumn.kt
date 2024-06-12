package com.neocalc.neocalc.history.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neocalc.neocalc.history.presentation.utils.hasReachedBottom

@Composable
fun <T> InfiniteScrollLazyColumn(
	modifier: Modifier = Modifier,
	loading: Boolean = false,
	loadingMore: Boolean = false,
	lazyListState: LazyListState = rememberLazyListState(),
	items: List<T>,
	itemKey: (T) -> Any,
	itemContent: @Composable (T, LazyListScope) -> Unit,
	loadingContent: @Composable () -> Unit,
	loadingMoreContent: @Composable () -> Unit,
	loadMore: () -> Unit,
	canLoadMoreData: Boolean,
	verticalArrangement: Arrangement.Vertical
) {
	val canLoadMoreDataState by rememberUpdatedState(newValue = canLoadMoreData)
	val canLoadMore by remember {
		derivedStateOf {
			canLoadMoreDataState && lazyListState.hasReachedBottom(1)
		}
	}

	LaunchedEffect(canLoadMore) {
		if (canLoadMore) loadMore()
	}

	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
		){

		if (loading) {
			loadingContent()
		} else {
			LazyColumn(
				state = lazyListState,
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = verticalArrangement
			) {
				items(
					items = items,
					key = { item: T -> itemKey(item) }
				) { item ->
					itemContent(item, this@LazyColumn)
				}

				if (loadingMore) {
					item {
						Spacer(modifier = Modifier.size(10.dp))
						loadingMoreContent()
					}
				}

			}
		}

	}

}