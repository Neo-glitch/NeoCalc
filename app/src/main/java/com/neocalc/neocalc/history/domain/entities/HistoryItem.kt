package com.neocalc.neocalc.history.domain.entities

data class HistoryItem(
	val type: HistoryType,
	val date: String? = null,
	val history : CalculationHistory? = null
)

enum class HistoryType {
	Date, History
}