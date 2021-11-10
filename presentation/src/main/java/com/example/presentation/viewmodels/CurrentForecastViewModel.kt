package com.example.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.models.current.CurrentForecastUiModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val currentForecastNetworkUseCase: UseCase<CurrentForecastDomainModel, CurrentForecastNetworkUseCaseArgument>,
    private val mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
) : BaseViewModel<CurrentContract.Event, CurrentContract.State, CurrentContract.Effect>() {

    override fun createInitialUiState(): CurrentContract.State {
        return CurrentContract.State(
            currentForecastState = CurrentContract.CurrentForecastState.Idle,
        )
    }

    override fun handleEvent(event: CurrentContract.Event) {
        when (event) {
            is CurrentContract.Event.OnFetchCurrentForecastNetwork -> {
                fetchCurrentForecastNetwork()
            }
        }
    }

    private fun fetchCurrentForecastNetwork() {
        // TODO: 10.11.2021 coordinates
        viewModelScope.launch {
            currentForecastNetworkUseCase.execute(
                argument = CurrentForecastNetworkUseCaseArgument(
                    latitude = 50.9,
                    longitude = 55.1
                )
            ).collect {
                handleEvents(it)
            }
        }
    }

    private fun handleEvents(resource: Resource<CurrentForecastDomainModel>) {
        when (resource) {
            is Resource.Loading -> {
                val data = mapper.from(resource.data)
                setState {
                    copy(
                        currentForecastState = CurrentContract.CurrentForecastState.Loading(
                            cashedForecast = data
                        )
                    )
                }
            }
            is Resource.Empty -> {
                setState { copy(currentForecastState = CurrentContract.CurrentForecastState.Idle) }
            }
            is Resource.Success -> {
                val data = mapper.from(resource.data)
                setState {
                    copy(
                        currentForecastState = CurrentContract.CurrentForecastState.Success(
                            forecast = data
                        )
                    )
                }
            }
            is Resource.Error -> {
                setEffect(CurrentContract.Effect.ShowError(message = resource.message))
            }
        }
    }
}