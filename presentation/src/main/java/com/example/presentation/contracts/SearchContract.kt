package com.example.presentation.contracts

import com.example.common.base.UiEffect
import com.example.common.base.UiEvent
import com.example.common.base.UiState
import com.example.presentation.models.current.CurrentForecastUiModel

class SearchContract {

    sealed class Event : UiEvent {
        data class OnFetchSearchForecastNetwork(
            val query: String
        ) : Event()
        data class OnChangeErrorLayoutState(
            val isVisibleNow: Boolean
        ) : Event()
    }

    data class State(
        val searchForecastState: SearchForecastState,
        val isErrorLayoutVisible: Boolean = false
    ) : UiState

    sealed class SearchForecastState {
        object Idle : SearchForecastState()
        object Loading : SearchForecastState()
        data class Success(val forecast: CurrentForecastUiModel) : SearchForecastState()
        object Error : SearchForecastState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}