package com.example.presentation.contracts

import com.example.common.base.UiEffect
import com.example.common.base.UiEvent
import com.example.common.base.UiState
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.models.search.SearchForecastUiModel

class SearchContract {

    sealed class Event : UiEvent {
        data class OnFetchSearchForecastNetwork(
            val query: String?
        ): Event()
    }

    data class State(
        val searchForecastState: SearchForecastState
    ): UiState

    sealed class SearchForecastState {
        object Idle : SearchForecastState()
        object Loading : SearchForecastState()
        data class Success(val forecast: SearchForecastUiModel) : SearchForecastState()
        object Error : SearchForecastState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}