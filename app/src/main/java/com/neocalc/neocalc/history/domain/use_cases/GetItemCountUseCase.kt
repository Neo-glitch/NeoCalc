package com.neocalc.neocalc.history.domain.use_cases

import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class GetItemCountUseCase(
	val repository: CalculationHistoryRepository
) {

	suspend operator fun invoke() : Int = repository.getHistoryItemCount()
}