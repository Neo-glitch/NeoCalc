package com.neocalc.neocalc.history.data.util

import androidx.room.TypeConverter
import java.util.Date

object DateTypeConverter {

	@TypeConverter
	fun toDate(dateInLong: Long) : Date = Date(dateInLong)
	@TypeConverter
	fun toLong(date: Date): Long = date.time
}