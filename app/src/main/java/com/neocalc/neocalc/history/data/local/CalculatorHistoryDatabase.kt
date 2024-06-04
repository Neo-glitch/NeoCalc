package com.neocalc.neocalc.history.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neocalc.neocalc.history.data.model.CalculationHistoryModel
import com.neocalc.neocalc.history.data.util.DateTypeConverter

@Database(entities = [CalculationHistoryModel::class], version = 1, exportSchema = false)
@TypeConverters(value = [DateTypeConverter::class])
abstract class CalculatorHistoryDatabase: RoomDatabase() {

	abstract val calculatorHistoryDao: CalculatorHistoryDao
}