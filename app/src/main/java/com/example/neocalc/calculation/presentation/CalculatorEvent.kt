package com.example.neocalc.calculation.presentation

sealed class CalculatorEvent {
    data class NumberEvent(val number: Int): CalculatorEvent()
    data object Clear: CalculatorEvent()
    data object Delete: CalculatorEvent()
    data object Decimal : CalculatorEvent()
    data object Calculate : CalculatorEvent()
    data class Operation(val operatorEvent: CalculatorOperation): CalculatorEvent()

}