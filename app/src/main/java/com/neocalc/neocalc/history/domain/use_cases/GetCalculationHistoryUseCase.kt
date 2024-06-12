package com.neocalc.neocalc.history.domain.use_cases

import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class GetCalculationHistoryUseCase(
	private val calculationHistoryRepository: CalculationHistoryRepository
) {

	suspend operator fun invoke(page: Int, pageSize: Int): List<CalculationHistory>{
		return calculationHistoryRepository.getHistory(page, pageSize)
	}
}