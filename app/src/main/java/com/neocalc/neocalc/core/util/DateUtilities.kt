package com.neocalc.neocalc.core.util


import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtilities {

	enum class DateFormatterPattern(val format: String) {
		DD_MM_YYYY ("dd/M/yyyy")
	}

	fun convertToDate(dateString: String, pattern: DateFormatterPattern): Date? {
		val formatter = SimpleDateFormat(pattern.format, Locale.getDefault())
		formatter.timeZone = TimeZone.getTimeZone("UTC")
		return try {
			formatter.parse(dateString)
		}catch(e: Exception){
			e.printStackTrace()
			null
		}
	}

	fun Date?.toFormattedDate(pattern: DateFormatterPattern) : String {
		if(this == null) return ""

		return try{
			if(isToday(time)){
				"Today"
			} else if(isYesterday(time)) {
				"Yesterday"
			} else {
				this.toDateString(pattern)
			}
		} catch(e: Exception){
			e.printStackTrace()
			""
		}
	}

	fun Date.toDateString(pattern: DateFormatterPattern) : String{
		return try {
			val formatter = SimpleDateFormat(pattern.format, Locale.getDefault())
			return formatter.format(this)
		} catch(e: Exception){
			val formatter = SimpleDateFormat(pattern.format, Locale.getDefault())
			""
		}
	}

	private fun isToday(timeStamp: Long) : Boolean {
		return DateUtils.isToday(timeStamp)
	}

	private fun isYesterday(timeStamp: Long): Boolean {
		return DateUtils.isToday(timeStamp + DateUtils.DAY_IN_MILLIS)
	}
}