package com.neocalc.neocalc.history.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.neocalc.neocalc.core.util.Constants
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel

@Dao
interface CalculatorHistoryDao {

	@Upsert
	suspend fun upsertCalculationHistory(history: CalculationHistoryModel)

	@Query("SELECT * FROM ${Constants.CALCULATION_HISTORY_TABLE} ORDER BY created_at DESC")
	suspend fun getCalculationHistory(): List<CalculationHistoryModel>

	@Query("SELECT * FROM ${Constants.CALCULATION_HISTORY_TABLE} ORDER BY created_at DESC LIMIT :pageSize OFFSET :pageOffset")
	suspend fun getHistory(pageSize: Int, pageOffset: Int) : List<CalculationHistoryModel>

	@Query("SELECT COUNT(*) FROM ${Constants.CALCULATION_HISTORY_TABLE}")
	suspend fun getItemCount(): Int

	@Query("DELETE FROM ${Constants.CALCULATION_HISTORY_TABLE}")
	suspend fun clearCalculationHistory()

}