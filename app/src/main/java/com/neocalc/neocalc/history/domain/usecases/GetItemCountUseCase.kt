package com.neocalc.neocalc.history.domain.usecases

import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class GetItemCountUseCase(
	val repository: CalculationHistoryRepository
) {

	suspend operator fun invoke() : Int = repository.getHistoryItemCount()
}