package com.example.cleanweatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.common.base.Mapper
import com.example.common.base.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.qualifiers.ViewModelKey
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.domain.usecases.search.SearchForecastNetworkUseCaseArgument
import com.example.presentation.livedata.CurrentLocationLiveData
import com.example.presentation.livedata.InternetConnectionLiveData
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.models.search.SearchForecastUiModel
import com.example.presentation.viewmodels.CurrentForecastViewModel
import com.example.presentation.viewmodels.SearchForecastViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(value = CurrentForecastViewModel::class)
    @Provides
    fun provideCurrentForecastViewModel(
        networkUseCase: UseCase<CurrentForecastDomainModel, CurrentForecastNetworkUseCaseArgument>,
        localUseCase: UseCase<CurrentForecastDomainModel, Nothing>,
        mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>,
        locationProviderClient: FusedLocationProviderClient,
        internetConnectionLiveData: InternetConnectionLiveData,
        locationLiveData: CurrentLocationLiveData
    ): ViewModel {
        return CurrentForecastViewModel(
            currentForecastNetworkUseCase = networkUseCase,
            currentForecastLocalUseCase = localUseCase,
            mapper = mapper,
            locationProviderClient = locationProviderClient,
            internetConnectionLiveData = internetConnectionLiveData,
            currentLocationLiveData = locationLiveData
        )
    }

    @IntoMap
    @ViewModelKey(value = SearchForecastViewModel::class)
    @Provides
    fun provideSearchForecastViewModel(
        searchUseCase: UseCase<SearchForecastDomainModel, SearchForecastNetworkUseCaseArgument>,
        mapper: Mapper<SearchForecastDomainModel, SearchForecastUiModel>,
        internetConnectionLiveData: InternetConnectionLiveData
    ): ViewModel {
        return SearchForecastViewModel(
            searchForecastNetworkUseCase = searchUseCase,
            mapper = mapper,
            internetConnectionLiveData = internetConnectionLiveData
        )
    }
}