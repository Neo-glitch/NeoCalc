package com.neocalc.neocalc.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.domain.usecases.GetAppThemeUseCase
import com.neocalc.neocalc.core.domain.usecases.SaveAppThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
	private val saveAppThemeUseCase: SaveAppThemeUseCase,
	private val getAppThemeUseCase: GetAppThemeUseCase
) : ViewModel() {

	private val _appTheme = MutableStateFlow(runBlocking { getAppThemeUseCase().first() })
	val appTheme = _appTheme.asStateFlow()

	init {
		onEvent(ThemeEvent.GetAppTheme)
	}

	fun onEvent(event: ThemeEvent) {
		when (event) {
			ThemeEvent.GetAppTheme -> getAppTheme()
			is ThemeEvent.SaveAppTheme -> saveAppTheme(event.appTheme)
		}
	}

	private fun getAppTheme() {
		viewModelScope.launch {
			getAppThemeUseCase().collectLatest { theme ->
				_appTheme.value = theme
			}
		}
	}

	private fun saveAppTheme(appTheme: AppTheme) {
		viewModelScope.launch {
			saveAppThemeUseCase(appTheme)
		}
	}


}