package com.neocalc.neocalc.history.domain.usecases

import com.neocalc.neocalc.history.data.mapper.toModel
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class UpsertCalculationHistoryUseCase(
	private val calculationHistoryRepository: CalculationHistoryRepository
) {

	suspend operator fun invoke(history: CalculationHistory) {
		calculationHistoryRepository.upsertCalculationHistory(history.toModel())
	}
}