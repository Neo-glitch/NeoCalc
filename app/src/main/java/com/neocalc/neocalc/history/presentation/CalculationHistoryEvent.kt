package com.neocalc.neocalc.history.presentation

sealed class CalculationHistoryEvent {
	data object Fetch: CalculationHistoryEvent()
	data object Clear: CalculationHistoryEvent()
}