package com.neocalc.neocalc.history.data.repository

import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.local.CalculatorHistoryDatabase
import com.neocalc.neocalc.history.data.mapper.toEntity
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class CalculationHistoryRepositoryImpl(
	historyDb: CalculatorHistoryDatabase
) : CalculationHistoryRepository {

	private val historyDao = historyDb.calculatorHistoryDao

	override suspend fun upsertCalculationHistory(historyModel: CalculationHistoryModel) {
		historyDao.upsertCalculationHistory(historyModel)
	}

	override suspend fun getCalculationHistory(): Resource {
		return try {
			val history = historyDao.getCalculationHistory().map{ it.toEntity() }
			Resource.Success(history)
		}catch (e: Exception){
			Resource.Error(e.localizedMessage ?: "Error Fetching History")
		}
	}

	override suspend fun clearCalculationHistory() {
		historyDao.clearCalculationHistory()
	}
}