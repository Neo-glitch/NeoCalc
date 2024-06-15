package com.neocalc.neocalc.core.domain.manager

import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppSettingsManager {
	suspend fun saveAppTheme(appTheme: AppTheme)
	fun getAppTheme(): Flow<AppTheme>
}