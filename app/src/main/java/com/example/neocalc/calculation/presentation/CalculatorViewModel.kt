package com.example.neocalc.calculation.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.faendir.rhino_android.RhinoAndroidHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.mozilla.javascript.Context

class CalculatorViewModel(
    private var applicationContext: Application
) : AndroidViewModel(applicationContext) {

    private val _uiState = MutableStateFlow(CalculatorScreenUiState())
    val uiState = _uiState.asStateFlow()
    private var context: Context? = null

    fun onEvent(event: CalculatorEvent){
        when(event){
            CalculatorEvent.Calculate -> calculate()
            CalculatorEvent.Clear -> clear()
            CalculatorEvent.Decimal -> enterDecimal()
            CalculatorEvent.Delete -> delete()
            is CalculatorEvent.NumberEvent -> enterNumber(event.number)
            is CalculatorEvent.Operation -> enterOperation(event.operatorEvent)
        }
    }

    private fun enterNumber(number: Int) {
        val state = uiState.value
        _uiState.update {
            it.copy(input = state.input + number, result = "")
        }
        performCalculationOnInputChange()
    }

    private fun enterOperation(operatorEvent: CalculatorOperation) {
        val state = uiState.value
        var input = state.input

        if (state.input.isLastCharOperator()) {
            // last character is operator, replace it
            val substring = input.substring(0, input.length - 1)
            input = substring + operatorEvent.symbol
            _uiState.update {
                it.copy(input = input)
            }
            performCalculationOnInputChange()
            return
        }

        if(input.isBlank()){
            if(operatorEvent == CalculatorOperation.Add || operatorEvent == CalculatorOperation.Subtract){
                input += operatorEvent.symbol
                _uiState.update { it.copy(input = input) }
            }
            return
        }

        input += operatorEvent.symbol
        _uiState.update {
            it.copy(input = input)
        }
        performCalculationOnInputChange()
    }

    private fun enterDecimal() {
        val state = uiState.value
        var firstNumber = state.input
        if (firstNumber.canAddDecimal()) {
            firstNumber += "."
            _uiState.update { it.copy(input = firstNumber) }
            performCalculationOnInputChange()
        }
    }

    private fun delete() {
        val state = uiState.value
      when{
          state.input.isNotBlank() -> {
              val input = uiState.value.input
              _uiState.update {
                  it.copy(input = state.input.substring(0, input.length - 1))
              }
              performCalculationOnInputChange()
          }
      }

    }

    private fun calculate() {
        val state = uiState.value
        var input = state.input
        if (input.isBlank()) {
            return
        }

        input = input.replace("÷", "/")
        input = input.replace("%", "/100")

        val rhinoAndroidHelper = RhinoAndroidHelper(applicationContext)
        context = rhinoAndroidHelper.enterContext()
        context?.optimizationLevel = -1
        try {
            val scriptable = context?.initStandardObjects()
            val resultString =
                context?.evaluateString(scriptable, input, "javascript", 1, null).toString()
            val resultDouble = resultString.toDoubleOrNull() ?: 0.0
            val result = resultDouble.cleanDouble().toString()
            _uiState.update { it.copy(input = result, isError = false, result = "") }
        } catch (e: Exception) {
            _uiState.update { it.copy(isError = true, result = "Format Error") }
        }
    }

    private fun performCalculationOnInputChange(){
        val state = uiState.value
        var input = state.input

        if (input.isBlank()) {
            _uiState.update { it.copy(result = "") }
            return
        }

        input = input.replace("÷", "/")
        input = input.replace("%", "/100")

        val rhinoAndroidHelper = RhinoAndroidHelper(applicationContext)
        context = rhinoAndroidHelper.enterContext()
        context?.optimizationLevel = -1

        val result = try {
            val scriptable = context?.initStandardObjects()
            val resultString =
                context?.evaluateString(scriptable, input, "javascript", 1, null).toString()
            val resultDouble = resultString.toDoubleOrNull() ?: 0.0
            resultDouble.cleanDouble().toString()
        } catch (e: Exception) {
            ""
        }
        _uiState.update { it.copy(result = result, isError = false) }
    }

    private fun clear(){
        // resets the state
        _uiState.update { CalculatorScreenUiState() }
    }

    private fun Double.cleanDouble(): Number{
        if(this % 1 == 0.0){
            // has no fractional part return int
            return toInt()
        }

        return this
    }

    private fun String.isLastCharOperator(): Boolean {
        if (isBlank()) return false

        val lastChar = this.last().toString()
        return (lastChar == CalculatorOperation.Subtract.symbol
                || lastChar == CalculatorOperation.Add.symbol
                || lastChar == CalculatorOperation.Divide.symbol
                || lastChar == CalculatorOperation.Multiply.symbol)
    }

    private fun String.isLastCharDecimal(): Boolean {
        if (isBlank()) return false
        val lastChar = this.last().toString()

        return lastChar == "."
    }

    private fun String.canAddDecimal(): Boolean {
        if (isLastCharDecimal()) {
            return false
        }
        return true
    }

    companion object{
        private const val MAX_NUM_LENGTH = 8
    }
}