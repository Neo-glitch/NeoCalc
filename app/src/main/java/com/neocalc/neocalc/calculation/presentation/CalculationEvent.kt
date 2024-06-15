package com.neocalc.neocalc.calculation.presentation

import com.neocalc.neocalc.calculation.domain.entities.AppTheme

sealed class CalculationEvent {
    data class InputNumber(val number: Int): CalculationEvent()
    data object Clear: CalculationEvent()
    data object Delete: CalculationEvent()
    data object InputDecimal : CalculationEvent()
    data object Calculate : CalculationEvent()
    data class Operation(val operatorOperation: CalculatorOperation): CalculationEvent()

}