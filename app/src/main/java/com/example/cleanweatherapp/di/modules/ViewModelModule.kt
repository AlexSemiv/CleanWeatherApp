package com.example.cleanweatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.common.other.Mapper
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.qualifiers.ViewModelKey
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.presentation.livedata.InternetConnectionLiveData
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.viewmodels.CurrentForecastViewModel
import com.example.presentation.viewmodels.InternetConnectionViewModel
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
        internetConnectionLiveData: InternetConnectionLiveData
    ): ViewModel {
        return CurrentForecastViewModel(
            currentForecastNetworkUseCase = networkUseCase,
            currentForecastLocalUseCase = localUseCase,
            mapper = mapper,
            locationProviderClient = locationProviderClient,
            internetConnectionLiveData = internetConnectionLiveData
        )
    }

    @IntoMap
    @ViewModelKey(value = InternetConnectionViewModel::class)
    @Provides
    fun provideInternetConnectionViewModel(
        internetConnectionLiveData: InternetConnectionLiveData
    ) : ViewModel {
        return InternetConnectionViewModel(
            internetConnectionLiveData = internetConnectionLiveData
        )
    }
}