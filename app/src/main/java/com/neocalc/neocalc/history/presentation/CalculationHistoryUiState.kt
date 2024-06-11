package com.neocalc.neocalc.history.presentation

import com.neocalc.neocalc.history.domain.entities.HistoryItem
import com.neocalc.neocalc.history.domain.entities.ListState

data class CalculationHistoryUiState(
	val isLoading: Boolean = false,
	val page: Int = 1,
	val canPaginate : Boolean = false,
	val listState : ListState = ListState.IDLE,
	val totalItemCount: Int = 0,
	val history: List<HistoryItem> = listOf(),
)