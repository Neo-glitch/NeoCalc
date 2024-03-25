package com.neocalc.neocalc.calculation.util

import com.neocalc.neocalc.calculation.presentation.CalculatorOperation


fun Double.cleanDouble(): Number{
    if(this % 1 == 0.0){
        // has no fractional part return int
        return toInt()
    }

    return this
}

fun String.isLastCharOperator(): Boolean {
    if (isBlank()) return false

    val lastChar = this.last().toString()
    return (lastChar == CalculatorOperation.Subtract.symbol
            || lastChar == CalculatorOperation.Add.symbol
            || lastChar == CalculatorOperation.Divide.symbol
            || lastChar == CalculatorOperation.Multiply.symbol)
}

fun String.isLastCharDecimal(): Boolean {
    if (isBlank()) return false
    val lastChar = this.last().toString()

    return lastChar == "."
}

fun String.canAddDecimal(): Boolean {
    if (isLastCharDecimal()) {
        return false
    }
    return true
}
