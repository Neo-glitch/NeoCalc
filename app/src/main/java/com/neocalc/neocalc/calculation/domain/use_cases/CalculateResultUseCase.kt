package com.neocalc.neocalc.calculation.domain.use_cases

import android.app.Application
import com.neocalc.neocalc.calculation.data.repository.CalculationRepositoryImpl
import com.neocalc.neocalc.calculation.domain.repository.CalculationRepository
import com.neocalc.neocalc.core.data.util.Resource

class CalculateResultUseCase (
    private val calculateRepository: CalculationRepository
){

    operator fun invoke(inputString: String): Resource {
        var input = inputString
        input = input.replace("÷", "/")
        input = input.replace("%", "/100")
        input = input.replace("x", "*")

        return calculateRepository.calculate(input)
    }
}