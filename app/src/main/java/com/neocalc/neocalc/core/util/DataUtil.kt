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

fun String.isLastCharBasicOperator(): Boolean {
    if (isBlank()) return false

    val lastChar = this.last().toString()
    return (lastChar == CalculatorOperation.Subtract.symbol
            || lastChar == CalculatorOperation.Add.symbol
            || lastChar == CalculatorOperation.Divide.symbol
            || lastChar == CalculatorOperation.Multiply.symbol)
}

fun String.isPenultimateCharPercent(): Boolean {
    if (this.length > 1) {
        val penultimateChar = this[this.length - 2].toString()
        return penultimateChar == CalculatorOperation.Percent.symbol
    }
    return false
}


/**
 * splits input string using operators
 * e.g Input: "87%.2%5" -> Output: [87, .2, 5]
 * e.g Input: "87+" -> Output: [87]
 * e.g Input: "87%+.2%5" -> Output: [87, , .2, 5]
 */
fun String.splitByCalculationOperators() : List<String> {
    val operationSplitRegex = Regex("[+\\-x÷%]")
    return split(operationSplitRegex)
}


fun String.canAddDecimal(): Boolean {
    // splits input string using operands and check is last input has decimal already
    // when it doesn't then decimal can be added, else false
    val singleInputParts = splitByCalculationOperators()

    singleInputParts.takeIf { it.isNotEmpty() }?.let {
        return !it.last().contains(".")
    }  ?: return false
}

fun String?.containsCalculatorOperator() : Boolean {
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
