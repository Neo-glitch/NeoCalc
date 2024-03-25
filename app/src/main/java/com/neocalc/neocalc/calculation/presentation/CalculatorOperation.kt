package com.neocalc.neocalc.calculation.presentation

sealed class CalculatorOperation (val symbol: String){
    data object Add: CalculatorOperation("+")
    data object Subtract: CalculatorOperation("-")
    data object Multiply: CalculatorOperation("x")
    data object Divide : CalculatorOperation("÷")
    data object Percent: CalculatorOperation("%")
}