package com.neocalc.neocalc.core.data.datasources.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AppPreferencesImpl (
	private val datastore: DataStore<Preferences>
) : AppPreferences {

	override fun <T> getPreferences(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
		return datastore.data.catch { exception ->
			if(exception is IOException) {
				emit(emptyPreferences())
			} else {
				exception.printStackTrace()
//				throw exception
			}
		}.map {preferences ->
			preferences[key] ?: defaultValue
		}
	}

	override fun <T> getFirstPreferences(key: Preferences.Key<T>, defaultValue: T): T {
		return runBlocking { datastore.data.first() } [key] ?: defaultValue
	}

	override suspend fun <T> putPreferences(key: Preferences.Key<T>, value: T) {
		datastore.edit { preferences ->
			preferences[key] = value
		}
	}

	override suspend fun <T> removePreference(key: Preferences.Key<T>) {
		datastore.edit { preferences ->
			preferences.remove(key)
		}
	}

	override suspend fun <T> clearAllPreferences() {
		datastore.edit { preferences ->
			preferences.clear()
		}
	}


}