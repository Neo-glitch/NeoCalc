package com.neocalc.neocalc.calculation.domain.usecases

class DeleteLastCharacterUseCase {

	operator fun invoke(input: String) : String{
		return input.substring(0, input.length - 1)
	}
}