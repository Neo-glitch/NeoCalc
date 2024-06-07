package com.neocalc.neocalc.calculation.domain.validation

import com.neocalc.neocalc.core.util.containsCalculatorOperator
import com.neocalc.neocalc.core.util.isLastCharBasicOperator
import com.neocalc.neocalc.core.util.isPenultimateCharPercent

object CalculationInputValidator {

	fun isCalculationInputValid(input: String): Boolean {
		return input.isNotBlank() &&
				input.containsCalculatorOperator() &&
				input.takeIf { it.isLastCharBasicOperator() }?.let {
					it.isPenultimateCharPercent()
				} ?: true
	}
}