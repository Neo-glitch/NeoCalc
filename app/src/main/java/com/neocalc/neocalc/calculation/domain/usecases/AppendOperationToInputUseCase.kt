package com.neocalc.neocalc.calculation.domain.usecases

import com.neocalc.neocalc.calculation.presentation.CalculatorOperation
import com.neocalc.neocalc.core.util.isLastCharBasicOperator

class AppendOperationToInputUseCase {

	operator fun invoke(input: String, operation: CalculatorOperation) : String{
		if(input.isLastCharBasicOperator()) {
			// last char is basic operator
			val inputWithoutLastOperatorChar = input.substring(0, input.length - 1)
			return inputWithoutLastOperatorChar + operation.symbol
		}

		return input + operation.symbol
	}
}