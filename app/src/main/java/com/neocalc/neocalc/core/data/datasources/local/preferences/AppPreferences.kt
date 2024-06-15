package com.neocalc.neocalc.core.data.datasources.local.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface AppPreferences {

	fun <T> getPreferences(key: Preferences.Key<T>, defaultValue: T) : Flow<T>
	fun <T> getFirstPreferences(key: Preferences.Key<T>, defaultValue: T): T
	suspend fun <T> putPreferences(key: Preferences.Key<T>, value: T)
	suspend fun <T> removePreference(key: Preferences.Key<T>)
	suspend fun <T> clearAllPreferences()
}