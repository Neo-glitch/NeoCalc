package com.neocalc.neocalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neocalc.neocalc.calculation.presentation.CalculatorScreen
import com.neocalc.neocalc.calculation.presentation.CalculatorViewModel
import com.neocalc.neocalc.ui.theme.NeoCalcTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NeoCalcTheme {
                // A surface container using the 'background' color from the theme

                SetBarColor(color = MaterialTheme.colorScheme.background)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewModel = viewModel<CalculatorViewModel>()
                    CalculatorScreen(viewModel = viewModel, buttonSpacing = 8.dp)
                }
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color){
    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // runs after every composition completes
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = !isSystemInDarkMode,
        )
    }
}