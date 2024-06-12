package com.neocalc.neocalc.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neocalc.neocalc.core.util.DateUtilities
import com.neocalc.neocalc.core.util.DateUtilities.DateFormatterPattern
import com.neocalc.neocalc.core.util.DateUtilities.toDateString
import com.neocalc.neocalc.core.util.DateUtilities.toFormattedDate
import com.neocalc.neocalc.history.domain.entities.CalculationHistory
import com.neocalc.neocalc.history.domain.entities.HistoryItem
import com.neocalc.neocalc.history.domain.entities.HistoryType
import com.neocalc.neocalc.history.domain.entities.ListState
import com.neocalc.neocalc.history.domain.use_cases.ClearCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.use_cases.GetCalculationHistoryUseCase
import com.neocalc.neocalc.history.domain.use_cases.GetItemCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
	private val getHistoryUseCase: GetCalculationHistoryUseCase,
	private val clearHistoryUseCase: ClearCalculationHistoryUseCase,
	private val getItemCountUseCase : GetItemCountUseCase
): ViewModel() {

	private val _uiState = MutableStateFlow(CalculationHistoryUiState())
	val uiState = _uiState.asStateFlow()

	// keeps map of items being show
	// key is date value and the list of items having that date
	private val historyItemsMap = mutableMapOf<String, List<CalculationHistory>>()

	init {
		getItemCount()
	}

	fun onEvent(event: CalculationHistoryEvent){
		when(event){
			CalculationHistoryEvent.Fetch -> getHistory()
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

	private fun getHistory() {
		viewModelScope.launch {
			_uiState.update { it.copy(isLoading = true) }
			var page = uiState.value.page
			var listState = uiState.value.listState
			var canPaginate = uiState.value.canPaginate
			var history = uiState.value.history
			val totalItemCount = uiState.value.totalItemCount


			if(page == 1 || (page != 1 && canPaginate) && listState == ListState.IDLE){
				listState = if (page == 1) ListState.LOADING else ListState.PAGINATING
				_uiState.update { it.copy(listState = listState)  }

				val result = getHistoryUseCase(page, 30)
				val historyItems = mapToHistoryItems(result)

				history = if (page == 1) {
					historyItems
				} else {
					listOf<HistoryItem>().plus(history.plus(historyItems))
				}

				canPaginate = history.filter{it.type == HistoryType.History}.size < totalItemCount
				listState = ListState.IDLE

				if(canPaginate)
					page += 1

				// update the uiState
				_uiState.update {
					it.copy(
						isLoading = false, page = page, canPaginate = canPaginate,
						listState = listState, history = history
					)
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

	private fun getItemCount() {
		viewModelScope.launch{
			val itemCount = getItemCountUseCase()
			_uiState.update { it.copy(totalItemCount = itemCount) }
			getHistory()
		}
	}

	override fun onCleared() {
		_uiState.update { it.copy(page = 1, listState = ListState.IDLE, canPaginate = false) }
		super.onCleared()
	}


	companion object{
		const val PAGE_SIZE = 30
	}

}