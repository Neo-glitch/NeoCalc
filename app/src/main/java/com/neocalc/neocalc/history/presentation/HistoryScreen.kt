package com.neocalc.neocalc.history.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ehsanmsz.mszprogressindicator.progressindicator.LineScaleProgressIndicator
import com.neocalc.neocalc.R
import com.neocalc.neocalc.history.domain.entities.HistoryType
import com.neocalc.neocalc.history.domain.entities.ListState
import com.neocalc.neocalc.history.presentation.components.InfiniteScrollLazyColumn

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HistoryScreen(
	viewModel : HistoryViewModel = hiltViewModel<HistoryViewModel>(),
	onPop: () -> Unit = {}
) {
	val state = viewModel.uiState.collectAsStateWithLifecycle()

	HistoryScreenContent(
		state  = state,
		onPop = onPop,
		onEvent = viewModel::onEvent
	)
}

@Composable
fun HistoryScreenContent(
	state: State<CalculationHistoryUiState> = mutableStateOf(CalculationHistoryUiState()),
	onPop: () -> Unit = {},
	onEvent: (CalculationHistoryEvent) -> Unit = {}
){
	Scaffold(
		topBar = {
			AppBarSection(
				state.value.history.isNotEmpty(),
				Modifier,
				onClear = { onEvent(CalculationHistoryEvent.Clear) },
				pop = onPop
			)
		}
	) { paddingValues ->
		InfiniteScrollLazyColumn(
			modifier = Modifier
				.padding(paddingValues = paddingValues)
				.background(MaterialTheme.colorScheme.surface)
				.fillMaxSize(),
			items = state.value.history,
			itemKey = { it.hashCode() },
			itemContent = { item, lazyListScope ->
				when (item.type) {
					HistoryType.Date -> {
						val isFirstItem = state.value.history.indexOf(item) == 0
						if (!isFirstItem) {
							HorizontalDivider(modifier = Modifier.fillMaxWidth())
						}
						DateItem(date = item.date ?: "")
					}

					HistoryType.History -> {
						HistoryItem(
							calculatorOperation = item.history?.calculation ?: "",
							result = item.history?.result ?: ""
						)
					}
				}
			},
			loadingContent = {
				LineScaleProgressIndicator(
					lineSpacing = 6.dp,
					minLineHeight = 20.dp,
					maxLineHeight = 50.dp,
					lineWidth = 5.dp,
					color = Color.Blue
				)
			},
			loadingMoreContent = {
				LineScaleProgressIndicator(
					lineSpacing = 6.dp,
					minLineHeight = 20.dp,
					maxLineHeight = 50.dp,
					lineWidth = 5.dp,
					color = Color.Blue
				)
			},
			loadMore = { onEvent(CalculationHistoryEvent.Fetch) },
			canLoadMoreData = state.value.canPaginate,
			verticalArrangement = Arrangement.spacedBy(20.dp),
			loading = state.value.listState == ListState.LOADING,
			loadingMore = state.value.listState == ListState.PAGINATING
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarSection(
	hasHistory: Boolean,
	modifier: Modifier = Modifier,
	onClear: () -> Unit = {},
	pop: () -> Unit = {}
) {
	TopAppBar(
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer
		),
		modifier = modifier
			.fillMaxWidth()
			.shadow(elevation = 4.dp),
		title = { Text(text = "History") },
		navigationIcon = {
			IconButton(onClick = pop) {
				Icon(
					imageVector = Icons.Filled.ArrowBack,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onSurface
				)
			}
		},
		actions = {

			if (hasHistory)
				IconButton(onClick = onClear) {
					Icon(
						painter = painterResource(id = R.drawable.ic_calculator_clear),
						contentDescription = null
					)
				}
		}
	)
}


@Composable
fun HistoryItem(
	calculatorOperation: String,
	result: String,
	modifier: Modifier = Modifier
) {
	Column(
		modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
	) {
		val calcScrollState = rememberScrollState()
		val resultScrollState = rememberScrollState()
		LaunchedEffect(key1 = calculatorOperation) {
			calcScrollState.scrollTo(calcScrollState.maxValue)
			resultScrollState.scrollTo(resultScrollState.maxValue)
		}

		Text(
			text = calculatorOperation,
			modifier = modifier
				.fillMaxWidth()
				.horizontalScroll(calcScrollState),
			maxLines = 1,
			textAlign = TextAlign.End,
			style = MaterialTheme.typography.titleLarge
		)

		Text(
			text = result,
			modifier = modifier
				.fillMaxWidth()
				.horizontalScroll(resultScrollState),
			maxLines = 1,
			textAlign = TextAlign.End,
			style = MaterialTheme.typography.titleLarge.copy(
				fontSize = 26.sp,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
			)
		)
	}
}

@Composable
fun DateItem(date: String, modifier: Modifier = Modifier) {
	Text(
		text = date,
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 16.dp),
		textAlign = TextAlign.Start,
		style = MaterialTheme.typography.titleMedium.copy(
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
		)
	)
}

