package com.neocalc.neocalc.history.domain.entities

import java.util.Date

data class CalculatorHistory(
	val id : Int,
	val calculation : String,
	val result: String,
	val createdAt: Date
)