package com.neocalc.neocalc.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neocalc.neocalc.calculation.presentation.CalculatorScreen
import com.neocalc.neocalc.calculation.presentation.CalculatorViewModel
import com.neocalc.neocalc.history.presentation.HistoryScreen
import com.neocalc.neocalc.history.presentation.HistoryViewModel


@Composable
fun AppNavGraph(navHostController: NavHostController) {
	NavHost(
		navController = navHostController,
		startDestination = AppScreen.Home.route
	) {

		composable(route = AppScreen.Home.route){
			val viewModel = hiltViewModel<CalculatorViewModel>()
			CalculatorScreen(viewModel = viewModel) {
				navHostController.navigate(AppScreen.History.route)
			}
		}

		composable(route = AppScreen.History.route){
//			val viewModel = viewModel<HistoryViewModel>()
			HistoryScreen(){
				navHostController.popBackStack()
			}
		}
	}
}