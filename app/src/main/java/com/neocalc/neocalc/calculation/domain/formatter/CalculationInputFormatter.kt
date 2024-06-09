package com.neocalc.neocalc.calculation.domain.formatter

import com.neocalc.neocalc.core.util.containsCalculatorOperator
import com.neocalc.neocalc.core.util.isLastCharBasicOperator
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder

object CalculationInputFormatter {

	fun formatInput(inputString: String): String {
		var input = inputString
		input = input.replace("÷", "/")
		input = input.replace("x", "*")

		val percentSymbolIndices = getPercentSymbolIndices(input)
		val nonOperatorIndicesAfterPercent = getNonOperatorIndicesAfterPercentSymbol(
			percentSymbolIndices, inputString
		)
		var newInput = addAsteriskBeforeNonOperatorSymbolAfterPercent(
			input, nonOperatorIndicesAfterPercent
		)

		newInput = newInput.replace("%", "*1/100")
		if(newInput.isLastCharBasicOperator()) newInput = newInput.dropLast(1)

		return newInput
	}

	private fun addAsteriskBeforeNonOperatorSymbolAfterPercent(
		input : String,
		indices: List<Int>
	) : String {
		val inputSb = StringBuilder(input)
		indices.forEach { index ->
			val char = input[index]
			inputSb.replace(index, index+1, "*$char")
		}
		return inputSb.toString()
	}

	private fun getPercentSymbolIndices(input: String): List<Int> {
		val indices = mutableListOf<Int>()
		var index = input.indexOf('%')

		while(index != -1) {
			// add index to index list, look for next percentage index in input
			indices.add(index)
			index = input.indexOf("%", index + 1)
		}
		return indices
	}

	private fun getNonOperatorIndicesAfterPercentSymbol(
		percentSymbolIndices : List<Int>,
		inputString: String
	) : List<Int> {
		val result = mutableListOf<Int>()
		percentSymbolIndices.forEach { index ->
			try {
				val afterPercentageIndex = index + 1
				val inputAfterPercentage = inputString[afterPercentageIndex].toString()
				if (!inputAfterPercentage.containsCalculatorOperator()) {
					result.add(afterPercentageIndex)
				}
			} catch (e: IndexOutOfBoundsException){
				// occurs if percentage char index
				e.printStackTrace()
			}
		}
		return result
	}
}