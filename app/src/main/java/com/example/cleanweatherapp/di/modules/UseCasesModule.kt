package com.example.cleanweatherapp.di.modules

import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.usecases.CurrentForecastUseCaseArgument
import com.example.domain.usecases.GetCurrentForecastUseCase
import com.example.domain.usecases.base.BaseUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCasesModule {

    @Binds
    abstract fun bindsGetCurrentForecastUseCase(
        useCase: GetCurrentForecastUseCase
    ): BaseUseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>
}