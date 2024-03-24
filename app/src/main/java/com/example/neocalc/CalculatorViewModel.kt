package com.example.neocalc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorScreenUiState())
    val uiState = _uiState.asStateFlow()

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
        if(state.operation == null){
            // since no operation entered, we are still entering first number
            if(state.firstNumber.length >= MAX_NUM_LENGTH){
                return
            }
            _uiState.update { it.copy(firstNumber = state.firstNumber + number) }
            return
        }
        if(state.secondNumber.length >= MAX_NUM_LENGTH){
            return
        }
        _uiState.update { it.copy(secondNumber = state.secondNumber + number) }
    }

    private fun enterOperation(operatorEvent: CalculatorOperation) {
        val state = uiState.value
        if(state.firstNumber.isNotBlank()){
            if(operatorEvent == CalculatorOperation.Percent){
                val firstNumber = state.firstNumber.toDoubleOrNull() ?: 0.0
                val result = firstNumber / 100
                _uiState.update {
                    it.copy(
                        firstNumber = result.cleanDouble().toString().take(15),
                        secondNumber = "",
                        operation = null
                    )
                }
                return
            }
            _uiState.update { it.copy(operation = operatorEvent) }
        }
    }

    private fun enterDecimal() {
        val state = _uiState.value
        if(state.operation == null && !state.firstNumber.contains(".") && state.firstNumber.isNotBlank()){
            _uiState.update { it.copy(firstNumber = state.firstNumber + ".") }
            return
        } else if(!state.secondNumber.contains(".") && state.secondNumber.isNotBlank()){
            _uiState.update { it.copy(secondNumber = state.secondNumber + ".") }
        }
    }

    private fun delete() {
        val state = uiState.value
      when{
          state.secondNumber.isNotBlank() -> _uiState.update {
              it.copy(secondNumber = state.secondNumber.dropLast(1))
          }
          state.operation != null -> _uiState.update {
              it.copy(operation = null)
          }
          state.firstNumber.isNotBlank() -> _uiState.update {
              it.copy(firstNumber = state.firstNumber.dropLast(1))
          }

      }

    }

    private fun calculate() {
        val state = uiState.value
        val firstNumber = state.firstNumber.toDoubleOrNull()
        val secondNumber = state.secondNumber.toDoubleOrNull()

        if(firstNumber != null && secondNumber != null){
            val result = when(state.operation){
                CalculatorOperation.Add -> firstNumber + secondNumber
                CalculatorOperation.Divide -> firstNumber / secondNumber
                CalculatorOperation.Multiply -> firstNumber * secondNumber
                CalculatorOperation.Percent -> null
                CalculatorOperation.Subtract -> firstNumber - secondNumber
                null -> return
            }
            _uiState.update {
                it.copy(
                    firstNumber = result?.cleanDouble().toString().take(15),
                    secondNumber = "",
                    operation = null

                )
            }
        }
    }

    private fun clear(){
        // resets the state
        _uiState.update { CalculatorScreenUiState() }
    }

    private fun Double.cleanDouble(): Number{
        if(this % 1 == 0.0){
            // has no fractional part return int
            return this.toInt()
        }

        return this
    }

    companion object{
        private const val MAX_NUM_LENGTH = 8
    }
}