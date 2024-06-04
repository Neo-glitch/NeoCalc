package com.neocalc.neocalc.core.util

import com.neocalc.neocalc.calculation.presentation.CalculatorOperation
import java.math.BigDecimal


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


fun String.canAddDecimal(): Boolean {
    // splits input string using operands and check is last input has decimal already
    // when it doesn't then decimal can be added, else false
    val operationSplitRegex = Regex("[+\\-x÷%]")
    val singleInputParts = this.split(operationSplitRegex)

    singleInputParts.takeIf { it.isNotEmpty() }?.let {
        return !it.last().contains(".")
    }  ?: return false
}

fun String?.containsCalculatorOperation() : Boolean {
    val calculatorOperations  = listOf("+", "-", "x", "÷", "%")
    return this?.let {text ->
        calculatorOperations.any { operation -> text.contains(operation) }
    } ?: false
}

fun BigDecimal.isIntegerValue() : Boolean {
    return (signum() == 0 || scale() <= 0 || stripTrailingZeros().scale() <= 0) &&
            // ensures this value doesn't also contain exponential
            !toString().contains("E")
}
