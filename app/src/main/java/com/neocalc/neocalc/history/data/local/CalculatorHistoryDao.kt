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

	@Query("SELECT * FROM ${Constants.CALCULATION_HISTORY_TABLE} order by createdAt desc")
	suspend fun getCalculationHistory(): List<CalculationHistoryModel>

	@Query("DELETE FROM ${Constants.CALCULATION_HISTORY_TABLE}")
	suspend fun clearCalculationHistory()

}