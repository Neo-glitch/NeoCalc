package com.neocalc.neocalc.calculation.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.neocalc.neocalc.calculation.data.repository.CalculateRepository
import com.neocalc.neocalc.calculation.domain.usecase.CalculateResultUseCase
import com.neocalc.neocalc.calculation.util.Resource
import com.neocalc.neocalc.calculation.util.canAddDecimal
import com.neocalc.neocalc.calculation.util.containsCalculatorOperation
import com.neocalc.neocalc.calculation.util.isLastCharOperator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.mozilla.javascript.Context

class CalculatorViewModel(
    private var applicationContext: Application
) : AndroidViewModel(applicationContext) {

    private val repository = CalculateRepository()
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
        _uiState.update {
            it.copy(input = uiState.value.input + number, result = "")
        }

        if(uiState.value.input.containsCalculatorOperation())
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

        val result = CalculateResultUseCase(repository).invoke(applicationContext, input)
        when (result) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    result = result.message,
                    isError = true
                )
            }

            is Resource.Success<*> -> _uiState.update {
                it.copy(
                    input = result.data as String,
                    isError = false,
                    result = ""
                )
            }
        }
    }

    private fun performCalculationOnInputChange(){
        val state = uiState.value
        var input = state.input

        if (input.isBlank()) {
            _uiState.update { it.copy(result = "") }
            return
        }

        val result = CalculateResultUseCase(repository).invoke(applicationContext, input)
        when (result) {
            is Resource.Error -> _uiState.update { it.copy(result = "", isError = false) }
            is Resource.Success<*> -> _uiState.update {
                it.copy(
                    result = result.data as String,
                    isError = false
                )
            }
        }
    }

    private fun clear(){
        // resets the state
        _uiState.update { CalculatorScreenUiState() }
    }


    companion object{
        private const val MAX_NUM_LENGTH = 8
    }
}