package com.neocalc.neocalc.calculation.domain.usecase

import android.app.Application
import com.neocalc.neocalc.calculation.data.repository.CalculateRepository
import com.neocalc.neocalc.calculation.util.Resource

class CalculateResultUseCase (
    private val calculateRepository: CalculateRepository
){

    operator fun invoke(applicationContext: Application, inputString: String): Resource{
        var input = inputString
        input = input.replace("÷", "/")
        input = input.replace("%", "/100")
        input = input.replace("x", "*")

        return calculateRepository.calculate(applicationContext, input)
    }
}