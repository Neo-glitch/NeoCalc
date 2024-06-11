package com.neocalc.neocalc.history.data.repository

import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.local.CalculatorHistoryDatabase
import com.neocalc.neocalc.history.data.mapper.toEntity
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.repository.CalculationHistoryRepository

class CalculationHistoryRepositoryImpl(
	historyDb: CalculatorHistoryDatabase
) : CalculationHistoryRepository {

	private val historyDao = historyDb.calculatorHistoryDao

	override suspend fun upsertCalculationHistory(historyModel: CalculationHistoryModel) {
		historyDao.upsertCalculationHistory(historyModel)
	}

	override suspend fun getCalculationHistory(): List<CalculationHistory> {
		return historyDao.getCalculationHistory().map { it.toEntity() }
	}

	override suspend fun getHistory(page: Int, pageSize: Int): List<CalculationHistory> {
		val offset = (page - 1) * pageSize
		return historyDao.getHistory(pageSize, offset).map { it.toEntity() }
	}

	override suspend fun getHistoryItemCount(): Int {
		return historyDao.getItemCount()
	}

	override suspend fun clearCalculationHistory() {
		historyDao.clearCalculationHistory()
	}
}