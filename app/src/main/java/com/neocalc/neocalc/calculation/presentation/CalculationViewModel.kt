package com.neocalc.neocalc.calculation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neocalc.neocalc.calculation.domain.formatter.CalculationInputFormatter
import com.neocalc.neocalc.calculation.domain.usecases.CalculateResultUseCase
import com.neocalc.neocalc.calculation.domain.usecases.FormatCalculationInputUseCase
import com.neocalc.neocalc.calculation.domain.usecases.ValidateCalculationInputUseCase
import com.neocalc.neocalc.calculation.domain.validation.CalculationInputValidator
import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.core.util.canAddDecimal
import com.neocalc.neocalc.core.util.isLastCharBasicOperator
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.usecases.UpsertCalculationHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(
    private val calculateResultUseCase: CalculateResultUseCase,
    private val upsertCalculationHistoryUseCase: UpsertCalculationHistoryUseCase,
    private val formatCalculationInputUseCase: FormatCalculationInputUseCase,
    private val validateCalculationInputUseCase: ValidateCalculationInputUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: CalculationEvent){
        when(event){
            CalculationEvent.Calculate -> calculate()
            CalculationEvent.Clear -> clear()
            CalculationEvent.InputDecimal -> enterDecimal()
            CalculationEvent.Delete -> delete()
            is CalculationEvent.InputNumber -> enterNumber(event.number)
            is CalculationEvent.Operation -> enterOperation(event.operatorOperation)
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

        if(!CalculationInputValidator.isCalculationInputValid(input)) return
        val formattedInput = CalculationInputFormatter.formatInput(input)

        when (val result = calculateResultUseCase(formattedInput)) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    result = result.message,
                    isError = true
                )
            }

            is Resource.Success<*> -> {
                saveOperationToHistory(
                    input, result.data as String
                )

                _uiState.update {
                    it.copy(
                        input = result.data as String,
                        isError = false,
                        result = "",
                        isEqualsOppDone = true
                    )
                }
            }
        }
    }

    private fun performCalculationOnInputChange(){
        val input = uiState.value.input

        if(!validateCalculationInputUseCase(input))
            return

        when (val result = calculateResultUseCase(formatCalculationInputUseCase(input))) {
            is Resource.Error -> _uiState.update { it.copy(result = "", isError = false) }
            is Resource.Success<*> -> {
                _uiState.update {
                    it.copy(
                        result = result.data as String,
                        isError = false
                    )
                }
            }
        }
    }

    private fun saveOperationToHistory (
        input: String, result: String
    ) {
        val date = Date(System.currentTimeMillis())
        val history = CalculationHistory(
            null, input, result, date
        )

        viewModelScope.launch {
            upsertCalculationHistoryUseCase(history)
        }
    }

    private fun clear(){
        // resets the state
        _uiState.update { CalculatorScreenUiState() }
    }
}
