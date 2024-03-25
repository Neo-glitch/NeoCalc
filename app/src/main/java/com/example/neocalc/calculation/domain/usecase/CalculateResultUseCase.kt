package com.example.neocalc.calculation.domain.usecase

import android.app.Application
import com.example.neocalc.calculation.data.repository.CalculateRepository
import com.example.neocalc.calculation.util.Resource
import com.example.neocalc.calculation.util.cleanDouble
import com.faendir.rhino_android.RhinoAndroidHelper
import kotlinx.coroutines.flow.update
import org.mozilla.javascript.Context

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