package com.neocalc.neocalc.calculation.presentation

data class CalculatorScreenUiState(
    val input: String = "",
    val result : String = "",
    val operation: CalculatorOperation? = null,
    val isError: Boolean = false
)