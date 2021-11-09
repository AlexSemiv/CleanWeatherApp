package com.example.presentation.contracts

import com.example.common.base.UiEffect
import com.example.common.base.UiEvent
import com.example.common.base.UiState
import com.example.presentation.models.current.CurrentForecastUiModel

class CurrentContract {

    sealed class Event : UiEvent {
        object OnFetchCurrentForecast : Event()

        data class OnSpinnerItemClicked(
            val position: Int
        ) : Event()
    }

    data class State(
        val currentForecastState: CurrentForecastState,
        val selectedSpinnerChartItem: Int = 0
    ) : UiState

    sealed class CurrentForecastState {
        object Idle : CurrentForecastState()
        object Loading : CurrentForecastState()
        data class Success(val forecast: CurrentForecastUiModel) : CurrentForecastState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
        data class ShowMoreInfoDialog(val forecast: CurrentForecastUiModel) : Effect()
    }
}