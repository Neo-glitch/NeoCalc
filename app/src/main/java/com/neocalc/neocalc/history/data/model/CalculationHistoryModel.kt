package com.neocalc.neocalc.history.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neocalc.neocalc.core.util.Constants
import java.util.Date

@Entity(tableName = Constants.CALCULATION_HISTORY_TABLE)
data class CalculationHistoryModel(
	@PrimaryKey(autoGenerate = true)
	val id : Int?,
	val calculation : String,
	val result: String,
	@ColumnInfo(name = "created_at")
	val createdAt: Date
)
