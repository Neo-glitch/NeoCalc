package com.neocalc.neocalc.core.domain.usecases

import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.domain.manager.AppSettingsManager

class SaveAppThemeUseCase(
	private val appSettingsManager: AppSettingsManager
) {

	suspend operator fun invoke (appTheme: AppTheme) {
		appSettingsManager.saveAppTheme(appTheme)
	}
}