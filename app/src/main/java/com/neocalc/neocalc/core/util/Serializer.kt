package com.neocalc.neocalc.core.util

import com.google.gson.Gson

// to be used in next version of release
class GsonSerializer (
	val gson : Gson
){
	fun <T> serialize(`object`: T): String {
		return gson.toJson(`object`)
	}

	inline fun <reified T> deserialize(jsonString: String?) : T? {
		return try {
			gson.fromJson(jsonString, T::class.java)
		} catch(e: Exception) {
			e.printStackTrace()
			null
		}
	}
}