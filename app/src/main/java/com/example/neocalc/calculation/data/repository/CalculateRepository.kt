package com.example.neocalc.calculation.data.repository

import android.app.Application
import com.example.neocalc.calculation.util.Resource
import com.example.neocalc.calculation.util.cleanDouble
import com.faendir.rhino_android.RhinoAndroidHelper
import org.mozilla.javascript.Context

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
            val resultDouble = resultString.toDoubleOrNull() ?: 0.0
            val result = resultDouble.cleanDouble().toString()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error("Format Error")
        }
    }
}