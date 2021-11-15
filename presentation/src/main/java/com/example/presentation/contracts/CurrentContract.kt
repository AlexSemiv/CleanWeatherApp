package com.example.presentation.contracts

import com.example.common.base.UiEffect
import com.example.common.base.UiEvent
import com.example.common.base.UiState
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.models.current.Daily

class CurrentContract {

    sealed class Event : UiEvent {
        object OnFetchCurrentForecastNetwork : Event()
        object OnFetchCurrentForecastLocal: Event()

        data class OnChangePermissionsState(val newState: CurrentPermissionState): Event()
        // changed units of measurement
        // show error layout
    }

    data class State(
        val currentForecastState: CurrentForecastState,

        val currentPermissionsState: CurrentPermissionState
        // units of measurement
        // error layout show
    ) : UiState

    sealed class CurrentForecastState {
        object Idle : CurrentForecastState()
        data class Loading(val cashedForecast: CurrentForecastUiModel?) : CurrentForecastState()
        data class Success(val forecast: CurrentForecastUiModel) : CurrentForecastState()
        data class Error(val cashedForecast: CurrentForecastUiModel? = null) : CurrentForecastState()
    }

    sealed class CurrentPermissionState {
        object Idle : CurrentPermissionState()
        object PermissionsGranted: CurrentPermissionState()
        object PermissionsDenied: CurrentPermissionState()
        object PermissionsPermanentlyDenied: CurrentPermissionState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
        data class ShowMoreInfoCurrentDialog(val forecast: CurrentForecastUiModel) : Effect()
        data class ShowMoreInfoDailyDialog(val daily: Daily) : Effect()
        object ShowPermissionsRequiredDialog: Effect()
    }
}