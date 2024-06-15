package com.neocalc.neocalc.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.neocalc.neocalc.calculation.presentation.CalculationViewModel
import com.neocalc.neocalc.calculation.presentation.CalculatorScreen
import com.neocalc.neocalc.core.presentation.ThemeViewModel
import com.neocalc.neocalc.core.util.getActivity
import com.neocalc.neocalc.history.presentation.HistoryScreen
import com.neocalc.neocalc.history.presentation.HistoryViewModel


@Composable
fun AppNavGraph(navHostController: NavHostController) {
	NavHost(
		navController = navHostController,
		startDestination = AppScreen.Home.route
	) {

		composable(route = AppScreen.Home.route){
			val themeViewModel = hiltViewModel<ThemeViewModel>()
			val calculationViewModel = hiltViewModel<CalculationViewModel>()

			CalculatorScreen(
				calculationViewModel = calculationViewModel,
				themeViewModel = themeViewModel
			) {
				navHostController.navigate(AppScreen.History.route)
			}
		}

		composable(route = AppScreen.History.route){
			val viewModel = hiltViewModel<HistoryViewModel>()
			HistoryScreen(
				viewModel = viewModel,
				onPop = {navHostController.popBackStack()}
			)
		}
	}
}