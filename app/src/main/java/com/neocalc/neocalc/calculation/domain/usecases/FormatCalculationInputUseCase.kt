package com.neocalc.neocalc.calculation.domain.usecases

import com.neocalc.neocalc.calculation.domain.formatter.CalculationInputFormatter

class FormatCalculationInputUseCase {

	operator fun invoke(input: String) : String {
		return CalculationInputFormatter.formatInput(input)
	}
}