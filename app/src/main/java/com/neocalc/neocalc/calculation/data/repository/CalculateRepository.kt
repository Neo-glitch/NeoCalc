package com.neocalc.neocalc.calculation.data.repository

import android.app.Application
import com.neocalc.neocalc.calculation.util.Resource
import com.neocalc.neocalc.calculation.util.cleanDouble
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
            val result =
                context?.evaluateString(scriptable, input, "javascript", 1, null).toString()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error("Format Error")
        }
    }
}