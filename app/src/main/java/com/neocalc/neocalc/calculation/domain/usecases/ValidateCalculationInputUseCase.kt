package com.neocalc.neocalc.calculation.domain.usecases

import com.neocalc.neocalc.calculation.domain.validation.CalculationInputValidator

class ValidateCalculationInputUseCase  {

	operator fun invoke(input: String) : Boolean{
		return CalculationInputValidator.isCalculationInputValid(input)
	}
}