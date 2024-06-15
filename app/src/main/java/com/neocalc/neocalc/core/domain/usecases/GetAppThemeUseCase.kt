package com.neocalc.neocalc.core.domain.usecases

import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.domain.manager.AppSettingsManager
import kotlinx.coroutines.flow.Flow

class GetAppThemeUseCase (
	private val appSettingsManager: AppSettingsManager
) {

	operator fun invoke(): Flow<AppTheme> = appSettingsManager.getAppTheme()
}