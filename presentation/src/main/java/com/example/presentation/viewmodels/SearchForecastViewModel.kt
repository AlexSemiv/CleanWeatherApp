package com.example.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.common.other.Resource
import com.example.common.other.UseCase
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.usecases.search.SearchForecastNetworkUseCaseArgument
import com.example.presentation.contracts.SearchContract
import com.example.presentation.livedata.InternetConnectionLiveData
import com.example.presentation.models.search.SearchForecastUiModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchForecastViewModel @Inject constructor(
    private val searchForecastNetworkUseCase: UseCase<SearchForecastDomainModel, SearchForecastNetworkUseCaseArgument>,
    private val mapper: Mapper<SearchForecastDomainModel, SearchForecastUiModel>,
    val internetConnectionLiveData: InternetConnectionLiveData
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {
    override fun createInitialUiState(): SearchContract.State {
        return SearchContract.State(
            searchForecastState = SearchContract.SearchForecastState.Idle
        )
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnFetchSearchForecastNetwork -> {
                fetchForecastByQuery(event.query)
            }
        }
    }

    private fun fetchForecastByQuery(query: String?) {
        viewModelScope.launch {
            searchForecastNetworkUseCase.execute(
                argument = SearchForecastNetworkUseCaseArgument(
                    query = query
                )
            ).onStart { emit(Resource.Loading()) }
                .collect {
                    handleData(it)
                }
        }
    }

    private fun handleData(resource: Resource<SearchForecastDomainModel>) {
        when (resource) {
            is Resource.Loading -> {
                setState {
                    copy(searchForecastState = SearchContract.SearchForecastState.Loading)
                }
            }
            is Resource.Empty -> {
                setState {
                    copy(searchForecastState = SearchContract.SearchForecastState.Idle)
                }
            }
            is Resource.Success -> {
                val data = mapper.from(resource.data)
                setState {
                    copy(
                        searchForecastState = SearchContract.SearchForecastState.Success(
                            forecast = data
                        )
                    )
                }
            }
            is Resource.Error -> {
                setState {
                    copy(searchForecastState = SearchContract.SearchForecastState.Error)
                }
                setEffect(
                    SearchContract.Effect.ShowError(
                        message = resource.message
                    )
                )
            }
        }
    }
}