package com.neocalc.neocalc.calculation.presentation

import androidx.lifecycle.ViewModel
import com.neocalc.neocalc.calculation.domain.formatter.CalculationInputFormatter
import com.neocalc.neocalc.calculation.domain.use_cases.CalculateResultUseCase
import com.neocalc.neocalc.calculation.domain.validation.CalculationInputValidator
import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.core.util.canAddDecimal
import com.neocalc.neocalc.core.util.isLastCharBasicOperator
import com.neocalc.neocalc.history.domain.use_cases.UpsertCalculationHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateResultUseCase: CalculateResultUseCase,
    private val upsertCalculationHistoryUseCase: UpsertCalculationHistoryUseCase
) : ViewModel() {


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
        val updatedInput = if (uiState.value.isEqualsOppDone) {
            _uiState.update { it.copy(isEqualsOppDone = false) }
            ""
        } else {
            uiState.value.input
        }.plus(number)

        _uiState.update {
            it.copy(input = updatedInput, result = "")
        }

        performCalculationOnInputChange()
    }

    private fun enterOperation(operatorEvent: CalculatorOperation) {
        val state = uiState.value
        var input = state.input

        if (state.input.isLastCharBasicOperator()) {
            // last character is operator, replace it
            val substring = input.substring(0, input.length - 1)
            input = substring + operatorEvent.symbol
            _uiState.update {
                it.copy(input = input, isEqualsOppDone = false)
            }
            performCalculationOnInputChange()
            return
        }

        if(input.isBlank()){
            if(operatorEvent == CalculatorOperation.Add || operatorEvent == CalculatorOperation.Subtract){
                input += operatorEvent.symbol
                _uiState.update { it.copy(input = input, isEqualsOppDone = false) }
            }
            return
        }

        input += operatorEvent.symbol
        _uiState.update {
            it.copy(input = input, isEqualsOppDone = false)
        }
        performCalculationOnInputChange()
    }

    private fun enterDecimal() {
        if (uiState.value.input.canAddDecimal()) {
            _uiState.update { it.copy(input = uiState.value.input.plus(".")) }
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
        val input = state.input

        // todo move this to a validator
//        if (input.isBlank()) return
//        if(!input.containsCalculatorOperation()) return

        if(!CalculationInputValidator.isCalculationInputValid(input)) return
        val formattedInput = CalculationInputFormatter.formatInput(input)

        when (val result = calculateResultUseCase(formattedInput)) {
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
                    result = "",
                    isEqualsOppDone = true
                )
            }
        }
    }

    private fun performCalculationOnInputChange(){
        val input = uiState.value.input

//        if (input.isBlank()) {
//            _uiState.update { it.copy(result = "") }
//            return
//        }
//
//        if (!input.containsCalculatorOperation()) return

        if(!CalculationInputValidator.isCalculationInputValid(input)) return
        val formattedInput = CalculationInputFormatter.formatInput(input)

        when (val result = calculateResultUseCase(formattedInput)) {
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