package com.example.cleanweatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.common.other.Mapper
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.qualifiers.ViewModelKey
import com.example.domain.usecases.CurrentForecastUseCaseArgument
import com.example.domain.usecases.base.BaseUseCase
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
        useCase: BaseUseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>,
        mapper: Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
    ): ViewModel {
        return CurrentForecastViewModel(
            currentForecastUseCase = useCase,
            mapper = mapper
        )
    }
}