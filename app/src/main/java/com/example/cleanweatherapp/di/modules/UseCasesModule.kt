package com.example.cleanweatherapp.di.modules

import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.qualifiers.ActivityScope
import com.example.domain.usecases.base.BaseUseCase
import com.example.domain.usecases.current.CurrentForecastUseCaseArgument
import com.example.domain.usecases.current.GetCurrentForecastUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCasesModule {

    @ActivityScope
    @Binds
    abstract fun bindsGetCurrentForecastUseCase(
        useCase: GetCurrentForecastUseCase
    ): BaseUseCase<CurrentForecastDomainModel, CurrentForecastUseCaseArgument>
}