package com.example.cleanweatherapp.di.modules

import com.example.common.other.Mapper
import com.example.data.mappers.CurrentForecastDataDomainMapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.presentation.mappers.CurrentForecastDomainUiMapper
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.remote.mappers.CurrentForecastNetworkDataMapper
import com.example.remote.models.current.CurrentForecastNetworkModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun bindsCurrentForecastNetworkDataMapper(
        mapper: CurrentForecastNetworkDataMapper
    ): Mapper<CurrentForecastNetworkModel, CurrentForecastDataModel>

    @Binds
    abstract fun bindsCurrentForecastDataDomainMapper(
        mapper: CurrentForecastDataDomainMapper
    ): Mapper<CurrentForecastDataModel, CurrentForecastDomainModel>

    @Binds
    abstract fun bindsCurrentForecastDomainUiMapper(
        mapper: CurrentForecastDomainUiMapper
    ): Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>
}