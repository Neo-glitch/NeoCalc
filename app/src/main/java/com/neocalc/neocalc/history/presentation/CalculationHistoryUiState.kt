package com.neocalc.neocalc.history.presentation

import com.neocalc.neocalc.history.domain.entities.HistoryItem

data class CalculationHistoryUiState(
	val isLoading: Boolean = false,
	val history: List<HistoryItem> = listOf(),
)