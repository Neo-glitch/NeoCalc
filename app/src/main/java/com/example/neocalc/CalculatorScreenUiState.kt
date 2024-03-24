package com.example.neocalc

data class CalculatorScreenUiState(
    val firstNumber: String = "",
    val secondNumber: String = "",
    val operation: CalculatorOperation? = null,
)