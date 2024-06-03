package com.neocalc.neocalc.history.presentation

import androidx.lifecycle.ViewModel
import com.neocalc.neocalc.history.domain.entities.CalculatorHistory
import com.neocalc.neocalc.history.domain.entities.HistoryEntity
import com.neocalc.neocalc.history.domain.entities.HistoryType
import java.util.Date

class HistoryViewModel : ViewModel() {

	fun getHistoryList() : List<HistoryEntity>{
		val item1 = HistoryEntity(HistoryType.date, date = "22/01/24")
		val item2 = HistoryEntity(HistoryType.history, history = CalculatorHistory(1, "Dummy Calc", "Dummy Result", Date()))
		val item3 = HistoryEntity(HistoryType.history, history = CalculatorHistory(2, "Dummy Calc2","Dummy Result", Date()))
		val item4 = HistoryEntity(HistoryType.history, history = CalculatorHistory(3, "Dummy Calc3", "Dummy Result",Date()))
		val item5 = HistoryEntity(HistoryType.date, date = "24/01/24")
		val item6 = HistoryEntity(HistoryType.history, history = CalculatorHistory(4, "Dummy Calc4", "Dummy Result",Date()))
		val item7 = HistoryEntity(HistoryType.history, history = CalculatorHistory(5, "Dummy Calc5", "Dummy Result",Date()))
		val item8 = HistoryEntity(HistoryType.date, date = "25/01/24")
		val item9 = HistoryEntity(HistoryType.history, history = CalculatorHistory(6, "Dummy Calc6", "Dummy Result", Date()))
		val item10 = HistoryEntity(HistoryType.history, history = CalculatorHistory(7, "Dummy Calc7", "Dummy Result", Date()))
		val item11 = HistoryEntity(HistoryType.history, history = CalculatorHistory(8, "Dummy Calc8", "Dummy Result", Date()))
		val item12 = HistoryEntity(HistoryType.history, history = CalculatorHistory(9, "Dummy Calc9", "Dummy Result", Date()))

		return listOf(
			item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12
		)
	}
}