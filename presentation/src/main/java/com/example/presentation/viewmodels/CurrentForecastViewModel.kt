package com.example.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.current.CurrentForecastUseCaseArgument
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.models.current.CurrentForecastUiModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val currentForecastUseCase: UseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>,
    private val mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
): BaseViewModel<CurrentContract.Event, CurrentContract.State, CurrentContract.Effect>() {

    override fun createInitialUiState(): CurrentContract.State {
        return CurrentContract.State(
            currentForecastState = CurrentContract.CurrentForecastState.Idle,
            selectedSpinnerChartItem = 0
        )
    }

    override fun handleEvent(event: CurrentContract.Event) {
        when(event) {
            is CurrentContract.Event.OnFetchCurrentForecast -> {
                fetchCurrentForecast(
                    latitude = event.latitude,
                    longitude = event.longitude,
                    units = event.units
                )
            }
            is CurrentContract.Event.OnSpinnerItemClicked -> {
                val position = event.position
                // TODO: 08.11.2021
            }
        }
    }

    private fun fetchCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ) {
        viewModelScope.launch {
            currentForecastUseCase.execute(
                CurrentForecastUseCaseArgument(
                    latitude = latitude,
                    longitude = longitude,
                    units = units
                )
            ).onStart { emit(Resource.Loading()) }
                .collect {
                    when(it) {
                        // TODO: 08.11.2021
                    }
                }
        }
    }
}