package com.neocalc.neocalc.navigation

sealed class AppScreen (
	val route: String,
	val title: String
){

	data object Home: AppScreen("/", "Home")
	data object History: AppScreen("/history", "History")
}