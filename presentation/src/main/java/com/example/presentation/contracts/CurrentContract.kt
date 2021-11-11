package com.example.presentation.contracts

import com.example.common.base.UiEffect
import com.example.common.base.UiEvent
import com.example.common.base.UiState
import com.example.presentation.models.current.CurrentForecastUiModel

class CurrentContract {

    sealed class Event : UiEvent {
        object OnFetchCurrentForecastNetwork : Event()
    }

    data class State(
        val currentForecastState: CurrentForecastState
    ) : UiState

    sealed class CurrentForecastState {
        object Idle : CurrentForecastState()
        data class Loading(val cashedForecast: CurrentForecastUiModel?) : CurrentForecastState()
        data class Success(val forecast: CurrentForecastUiModel) : CurrentForecastState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
        data class ShowMoreInfoDialog(val forecast: CurrentForecastUiModel) : Effect()
    }
}