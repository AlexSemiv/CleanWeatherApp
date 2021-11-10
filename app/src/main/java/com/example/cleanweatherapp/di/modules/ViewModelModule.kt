package com.example.cleanweatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.common.other.Mapper
import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.qualifiers.ViewModelKey
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.viewmodels.CurrentForecastViewModel
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
        mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
    ): ViewModel {
        return CurrentForecastViewModel(
            currentForecastNetworkUseCase = networkUseCase,
            mapper = mapper
        )
    }
}