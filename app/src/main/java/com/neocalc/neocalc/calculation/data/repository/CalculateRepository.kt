package com.neocalc.neocalc.calculation.data.repository

import android.app.Application
import com.neocalc.neocalc.calculation.util.Resource
import com.neocalc.neocalc.calculation.util.cleanDouble
import com.faendir.rhino_android.RhinoAndroidHelper
import com.neocalc.neocalc.calculation.util.isIntegerValue
import org.mozilla.javascript.Context
import java.math.BigDecimal

class CalculateRepository {

    fun calculate(applicationContext: Application, input: String): Resource{
        val context: Context?
        val rhinoAndroidHelper = RhinoAndroidHelper(applicationContext)
        context = rhinoAndroidHelper.enterContext()
        context?.optimizationLevel = -1

        // should move this to repository
        return try {
            val scriptable = context?.initStandardObjects()
            val resultString =
                context?.evaluateString(scriptable, input, "javascript", 1, null).toString()
//            val resultDouble = resultString.toDoubleOrNull() ?: 0.0
//            val result = resultDouble.cleanDouble().toString()
            val result = BigDecimal(resultString)
            val cleanedResult = if(result.isIntegerValue()){
                result.intValueExact().toString()
            } else{
                result.toString()
            }
            Resource.Success(cleanedResult)
        } catch (e: Exception) {
            Resource.Error("Format Error")
        }
    }

}