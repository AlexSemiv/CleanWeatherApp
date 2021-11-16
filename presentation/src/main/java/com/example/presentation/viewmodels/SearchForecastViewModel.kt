package com.example.presentation.viewmodels

import com.example.common.base.BaseViewModel
import com.example.common.other.Mapper
import com.example.common.other.UseCase
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.usecases.search.SearchForecastNetworkUseCaseArgument
import com.example.presentation.contracts.SearchContract
import com.example.presentation.livedata.InternetConnectionLiveData
import com.example.presentation.models.search.SearchForecastUiModel
import javax.inject.Inject

class SearchForecastViewModel @Inject constructor(
    private val searchForecastNetworkUseCase: UseCase<SearchForecastDomainModel, SearchForecastNetworkUseCaseArgument>,
    private val mapper: Mapper<SearchForecastDomainModel, SearchForecastUiModel>,
    val internetConnectionLiveData: InternetConnectionLiveData
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {
    override fun createInitialUiState(): SearchContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: SearchContract.Event) {
        TODO("Not yet implemented")
    }
}