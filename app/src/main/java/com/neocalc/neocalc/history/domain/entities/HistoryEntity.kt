package com.neocalc.neocalc.history.domain.entities

data class HistoryEntity(
	val type: HistoryType,
	val date: String? = null,
	val history : CalculationHistory? = null
)

enum class HistoryType {
	date, history
}