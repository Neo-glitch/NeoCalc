package com.neocalc.neocalc.core.presentation

import com.neocalc.neocalc.calculation.domain.entities.AppTheme

sealed class ThemeEvent {

	data object GetAppTheme: ThemeEvent()
	data class SaveAppTheme(val appTheme: AppTheme) : ThemeEvent()
}