package com.neocalc.neocalc.history.domain.usecases

import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class ClearCalculationHistoryUseCase(
	private val calculationHistoryRepository: CalculationHistoryRepository
) {

	suspend operator fun invoke() {
		calculationHistoryRepository.clearCalculationHistory()
	}
}