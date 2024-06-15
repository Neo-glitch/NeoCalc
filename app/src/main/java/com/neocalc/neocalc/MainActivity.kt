package com.neocalc.neocalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.neocalc.neocalc.ui.theme.NeoCalcTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.neocalc.neocalc.calculation.domain.entities.AppTheme
import com.neocalc.neocalc.core.navigation.AppNavGraph
import com.neocalc.neocalc.core.presentation.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<ThemeViewModel>()
            val theme = viewModel.appTheme.collectAsStateWithLifecycle()

            val useDarkTheme = when(theme.value) {
                AppTheme.DARK_MODE -> true
                AppTheme.LIGHT_MODE -> false
                AppTheme.SYSTEM_DEFAULT_MODE -> isSystemInDarkTheme()
            }

            NeoCalcTheme(
                darkTheme = useDarkTheme
            ) {
                SetBarColor(color = MaterialTheme.colorScheme.surfaceContainer, useDarkTheme)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navHostController = navController)
                }
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color, isDarkMode: Boolean){
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // runs after every composition completes
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = !isDarkMode,
        )
    }
}