package com.neocalc.neocalc.history.data.mapper

import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.entities.CalculationHistory

fun CalculationHistory.toModel(): CalculationHistoryModel{
	return CalculationHistoryModel(
		this.id,
		this.calculation,
		this.result,
		this.createdAt
	)
}

fun CalculationHistoryModel.toEntity() : CalculationHistory {
	return CalculationHistory(
		this.id,
		this.calculation,
		this.result,
		this.createdAt
	)
}
