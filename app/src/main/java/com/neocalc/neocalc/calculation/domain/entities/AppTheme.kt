package com.neocalc.neocalc.calculation.domain.entities

enum class AppTheme {
	DARK_MODE,
	LIGHT_MODE,
	SYSTEM_DEFAULT_MODE;

	companion object{
		fun fromOrdinal(ordinal: Int) = values()[ordinal]
	}
}