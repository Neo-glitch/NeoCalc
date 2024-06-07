package com.neocalc.neocalc.calculation.data.repository

import android.app.Application
import com.neocalc.neocalc.core.data.util.Resource
import com.faendir.rhino_android.RhinoAndroidHelper
import com.neocalc.neocalc.calculation.domain.repository.CalculationRepository
import com.neocalc.neocalc.core.util.isIntegerValue
import org.mozilla.javascript.Context
import java.math.BigDecimal

class CalculationRepositoryImpl(
    private val rhino: RhinoAndroidHelper
) : CalculationRepository {

    override fun calculate(input: String): Resource {
        val context = rhino.enterContext()
        context.optimizationLevel = -1

        // should move this to repository
        return try {
            val scriptable = context?.initStandardObjects()
            val resultString =
                context?.evaluateString(scriptable, input, "javascript", 1, null).toString()
            val result = BigDecimal(resultString)
            val cleanedResult = if(result.isIntegerValue()){
                result.toBigInteger().toString()
            } else{
                result.toString()
            }
            Resource.Success(cleanedResult)
        } catch (e: Exception) {
            Resource.Error("Format Error")
        }
    }

}