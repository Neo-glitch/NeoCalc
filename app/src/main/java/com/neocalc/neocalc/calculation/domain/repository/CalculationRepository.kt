package com.neocalc.neocalc.calculation.domain.repository

import com.neocalc.neocalc.core.data.util.Resource

interface CalculationRepository {

	fun calculate(input: String) : Resource
}