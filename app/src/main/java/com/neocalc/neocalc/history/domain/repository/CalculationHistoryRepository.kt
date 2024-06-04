package com.neocalc.neocalc.history.domain.repository

import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel

interface CalculationHistoryRepository {

	suspend fun upsertCalculationHistory(historyModel: CalculationHistoryModel)
	suspend fun getCalculationHistory(): Resource
	suspend fun clearCalculationHistory()

}
