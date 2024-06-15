package com.neocalc.neocalc.calculation.domain.usecases

import com.neocalc.neocalc.calculation.domain.formatter.CalculationInputFormatter
import com.neocalc.neocalc.calculation.domain.repository.CalculationRepository
import com.neocalc.neocalc.core.data.util.Resource

class CalculateResultUseCase (
    private val calculateRepository: CalculationRepository
){

    operator fun invoke(input: String): Resource {
        val formattedInput = CalculationInputFormatter.formatInput(input)

        return calculateRepository.calculate(formattedInput)
    }
}