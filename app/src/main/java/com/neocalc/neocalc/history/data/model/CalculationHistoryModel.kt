package com.neocalc.neocalc.history.data.model

import java.util.Date

data class CalculationHistoryModel(
	val id : Int?,
	val calculation : String,
	val createdAt: Date
)
