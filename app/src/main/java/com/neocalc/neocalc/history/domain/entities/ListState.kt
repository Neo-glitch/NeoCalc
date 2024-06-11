package com.neocalc.neocalc.history.domain.entities


// to manage state for manual pagination
enum class ListState {
	IDLE,
	LOADING,
	PAGINATING,
	ERROR,
	PAGINATION_EXHAUST
}