package com.neocalc.neocalc.core.data.manager

import androidx.datastore.preferences.core.intPreferencesKey
import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.data.datasources.local.preferences.AppPreferences
import com.neocalc.neocalc.core.domain.manager.AppSettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsManagerImpl (
	private val preferences: AppPreferences
) : AppSettingsManager {

	override suspend fun saveAppTheme(appTheme: AppTheme) {
		preferences.putPreferences(APP_THEME_KEY, appTheme.ordinal)
	}

	override fun getAppTheme(): Flow<AppTheme> {
		return preferences.getPreferences(APP_THEME_KEY, AppTheme.SYSTEM_DEFAULT_MODE.ordinal).map {
			AppTheme.fromOrdinal(it)
		}
	}

	companion object {
		val APP_THEME_KEY = intPreferencesKey("app_theme")
	}

}