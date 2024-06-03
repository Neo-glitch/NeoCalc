package com.neocalc.neocalc.history.domain.entities

data class HistoryEntity(
	val type: HistoryType,
	val date: String? = null,
	val history : CalculatorHistory? = null
)

enum class HistoryType {
	date, history
}