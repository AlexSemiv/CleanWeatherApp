package com.example.cleanweatherapp.di.modules

import com.example.common.other.UseCase
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.qualifiers.ActivityScope
import com.example.domain.usecases.current.CurrentForecastNetworkUseCaseArgument
import com.example.domain.usecases.current.GetCurrentForecastLocalUseCase
import com.example.domain.usecases.current.GetCurrentForecastNetworkUseCase
import com.example.domain.usecases.search.GetSearchForecastNetworkUseCase
import com.example.domain.usecases.search.SearchForecastNetworkUseCaseArgument
import dagger.Binds
import dagger.Module

@Module
abstract class UseCasesModule {

    @ActivityScope
    @Binds
    abstract fun bindsGetCurrentForecastNetworkUseCase(
        networkUseCase: GetCurrentForecastNetworkUseCase
    ): UseCase<CurrentForecastDomainModel, CurrentForecastNetworkUseCaseArgument>

    @ActivityScope
    @Binds
    abstract fun bindsGetCurrentForecastLocalUseCase(
        networkUseCase: GetCurrentForecastLocalUseCase
    ): UseCase<CurrentForecastDomainModel, Nothing>

    // search

    @ActivityScope
    @Binds
    abstract fun bindGetSearchForecastNetworkUseCase(
        searchUseCase: GetSearchForecastNetworkUseCase
    ): UseCase<SearchForecastDomainModel, SearchForecastNetworkUseCaseArgument>
}