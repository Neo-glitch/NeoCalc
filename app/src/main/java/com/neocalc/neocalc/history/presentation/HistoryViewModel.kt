package com.neocalc.neocalc.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neocalc.neocalc.core.data.util.Resource
import com.neocalc.neocalc.core.util.DateUtilities
import com.neocalc.neocalc.core.util.DateUtilities.DateFormatterPattern
import com.neocalc.neocalc.core.util.DateUtilities.toDateString
import com.neocalc.neocalc.core.util.DateUtilities.toFormattedDate
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.entities.HistoryItem
import com.neocalc.neocalc.history.domain.entities.HistoryType
import com.neocalc.neocalc.history.domain.use_cases.ClearCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.use_cases.GetCalculationHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
	private val getHistoryUseCase: GetCalculationHistoryUseCase,
	private val clearHistoryUseCase: ClearCalculationHistoryUseCase
): ViewModel() {

	private val _uiState = MutableStateFlow(CalculationHistoryUiState())
	val uiState = _uiState.asStateFlow()

	// keeps map of items being show
	// key is date value and the list of items having that date
	private val historyItemsMap = mutableMapOf<String, List<CalculationHistory>>()

	fun onEvent(event: CalculationHistoryEvent){
		when(event){
			CalculationHistoryEvent.Fetch -> getCalculationHistory()
			CalculationHistoryEvent.Clear -> clearHistory()
		}
	}

	private fun clearHistory() {
		viewModelScope.launch{
			clearHistoryUseCase()
			_uiState.update {
				it.copy(history = listOf())
			}
		}
	}

	private fun getCalculationHistory() {
		viewModelScope.launch{
			_uiState.update{ it.copy(isLoading = true) }
			when (val result = getHistoryUseCase()) {
				is Resource.Error -> {
					_uiState.update{it.copy(isLoading = false)}
				}
				is Resource.Success<*> -> {
					val history = result.data as List<*>
					val historyItems = mapToHistoryItems(history as List<CalculationHistory>)
					_uiState.update { it.copy(isLoading = false, history = historyItems) }
				}
			}
		}
	}

	private fun mapToHistoryItems(historyList : List<CalculationHistory>): List<HistoryItem>{
		val newHistoryItemsMap = historyList.groupBy {
			it.createdAt.toDateString(DateFormatterPattern.DD_MM_YYYY)
		}

		val result = newHistoryItemsMap.flatMap{ (dateString, historyEntities) ->
			if(historyItemsMap[dateString] == null) {
				val date = DateUtilities.convertToDate(dateString, DateFormatterPattern.DD_MM_YYYY)
				listOf(
					HistoryItem(
						type= HistoryType.Date,
						date = date.toFormattedDate(DateFormatterPattern.DD_MM_YYYY)
					)
				) + historyEntities.map { HistoryItem(type = HistoryType.History, history = it) }
			} else {
				historyEntities.map { HistoryItem(type = HistoryType.History, history = it) }
			}
		}
		updateHistoryItemsMap(newHistoryItemsMap)
		return result
	}

	private fun updateHistoryItemsMap(newHistoryItemsMap : Map<String, List<CalculationHistory>>) {
		newHistoryItemsMap.forEach{ (date, historyList) ->
			val currentItemsForKey = historyItemsMap[date]
			if (currentItemsForKey == null) {
				// item not in map that keeps track of history Loaded
				historyItemsMap[date] = historyList
			} else {
				historyItemsMap[date] = currentItemsForKey + historyList
			}
		}
	}

}