package com.neocalc.neocalc.history.domain.repository

import androidx.compose.foundation.pager.PageSize
import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.domain.entities.CalculationHistory

interface CalculationHistoryRepository {

	suspend fun upsertCalculationHistory(historyModel: CalculationHistoryModel)
	suspend fun getCalculationHistory(): List<CalculationHistory>

	suspend fun getHistory(page: Int, pageSize: Int): List<CalculationHistory>

	suspend fun getHistoryItemCount() : Int

	suspend fun clearCalculationHistory()

}
