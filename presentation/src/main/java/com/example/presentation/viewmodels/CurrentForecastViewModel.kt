package com.example.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.presentation.contracts.CurrentContract
import com.example.presentation.livedata.InternetConnectionLiveData
import com.example.presentation.models.current.CurrentForecastUiModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val currentForecastNetworkUseCase: UseCase<CurrentForecastDomainModel, CurrentForecastNetworkUseCaseArgument>,
    private val currentForecastLocalUseCase: UseCase<CurrentForecastDomainModel, Nothing>,
    private val mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>,
    private val locationProviderClient: FusedLocationProviderClient,
    val internetConnectionLiveData: InternetConnectionLiveData
) : BaseViewModel<CurrentContract.Event, CurrentContract.State, CurrentContract.Effect>() {

    override fun createInitialUiState(): CurrentContract.State {
        return CurrentContract.State(
            currentForecastState = CurrentContract.CurrentForecastState.Idle,
            currentPermissionsState = CurrentContract.CurrentPermissionState.Idle
        )
    }

    override fun handleEvent(event: CurrentContract.Event) {
        when (event) {
            is CurrentContract.Event.OnFetchCurrentForecastNetwork -> {
                fetchCurrentForecastNetwork()
            }
            is CurrentContract.Event.OnFetchCurrentForecastLocal -> {
                fetchCurrentForecastLocal()
            }
            is CurrentContract.Event.OnChangePermissionsState -> {
                handlePermissionsEvent(event.newState)
            }
        }
    }

    private fun fetchCurrentForecastLocal() {
        viewModelScope.launch {
            currentForecastLocalUseCase.execute(null)
                .collect {
                    handleDataEvents(it)
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchCurrentForecastNetwork() {
        viewModelScope.launch {
            val location = locationProviderClient.lastLocation
                .asDeferredAsync()
                .await()
            currentForecastNetworkUseCase.execute(
                argument = CurrentForecastNetworkUseCaseArgument(
                    latitude = location?.latitude,
                    longitude = location?.longitude
                )
            ).collect {
                handleDataEvents(it)
            }
        }
    }

    private fun <T> Task<T>.asDeferredAsync(): Deferred<T> {
        val deferred = CompletableDeferred<T>()
        addOnSuccessListener { result -> deferred.complete(result) }
        addOnFailureListener { ex -> deferred.completeExceptionally(ex) }
        return deferred
    }

    private fun handleDataEvents(resource: Resource<CurrentForecastDomainModel>) {
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
                val data = mapper.from(resource.data)
                setState {
                    copy(
                        currentForecastState = CurrentContract.CurrentForecastState.Error(
                            cashedForecast = data
                        )
                    )
                }
                setEffect(
                    CurrentContract.Effect.ShowError(message = resource.message)
                )
            }
        }
    }

    private fun handlePermissionsEvent(newState: CurrentContract.CurrentPermissionState) {
        setState { copy(currentPermissionsState = newState) }
    }
}