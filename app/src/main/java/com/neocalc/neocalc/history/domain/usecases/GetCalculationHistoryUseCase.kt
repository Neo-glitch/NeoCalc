package com.neocalc.neocalc.history.domain.usecases

import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class GetCalculationHistoryUseCase(
	private val calculationHistoryRepository: CalculationHistoryRepository
) {

	suspend operator fun invoke(page: Int, pageSize: Int): List<CalculationHistory>{
		return calculationHistoryRepository.getHistory(page, pageSize)
	}
}