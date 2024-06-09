package com.neocalc.neocalc.history.domain.use_cases

import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class ClearCalculationHistoryUseCase(
	private val calculationHistoryRepository: CalculationHistoryRepository
) {

	suspend operator fun invoke() {
		calculationHistoryRepository.clearCalculationHistory()
	}
}