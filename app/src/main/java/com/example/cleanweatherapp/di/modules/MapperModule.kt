package com.example.cleanweatherapp.di.modules

import com.example.common.other.Mapper
import com.example.data.mappers.CurrentForecastDataDomainMapper
import com.example.data.mappers.SearchForecastDataDomainMapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.data.models.search.SearchForecastDataModel
import com.example.domain.models.current.CurrentForecastDomainModel
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.qualifiers.ActivityScope
import com.example.local.mapper.CurrentForecastLocalDataMapper
import com.example.local.model.current.CurrentForecastLocalModel
import com.example.presentation.mappers.CurrentForecastDomainUiMapper
import com.example.presentation.mappers.SearchForecastDomainUiModel
import com.example.presentation.models.current.CurrentForecastUiModel
import com.example.presentation.models.search.SearchForecastUiModel
import com.example.remote.mappers.CurrentForecastNetworkDataMapper
import com.example.remote.mappers.SearchForecastNetworkDataMapper
import com.example.remote.models.current.CurrentForecastNetworkModel
import com.example.remote.models.search.SearchForecastNetworkModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @ActivityScope
    @Binds
    abstract fun bindsCurrentForecastNetworkDataMapper(
        mapper: CurrentForecastNetworkDataMapper
    ): Mapper<CurrentForecastNetworkModel, CurrentForecastDataModel>

    @ActivityScope
    @Binds
    abstract fun bindsCurrentForecastDataDomainMapper(
        mapper: CurrentForecastDataDomainMapper
    ): Mapper<CurrentForecastDataModel, CurrentForecastDomainModel>

    @ActivityScope
    @Binds
    abstract fun bindsCurrentForecastDomainUiMapper(
        mapper: CurrentForecastDomainUiMapper
    ): Mapper<CurrentForecastDomainModel, CurrentForecastUiModel>

    @ActivityScope
    @Binds
    abstract fun bindsCurrentForecastLocalDataMapper(
        mapper: CurrentForecastLocalDataMapper
    ): Mapper<CurrentForecastLocalModel, CurrentForecastDataModel>

    // search

    @ActivityScope
    @Binds
    abstract fun bindsMapperSearchForecastNetworkData(
        mapper: SearchForecastNetworkDataMapper
    ): Mapper<SearchForecastNetworkModel, SearchForecastDataModel>

    @ActivityScope
    @Binds
    abstract fun bindsMapperSearchForecastDataDomain(
        mapper: SearchForecastDataDomainMapper
    ): Mapper<SearchForecastDataModel, SearchForecastDomainModel>

    @ActivityScope
    @Binds
    abstract fun bindsMapperSearchForecastDomainUi(
        mapper: SearchForecastDomainUiModel
    ): Mapper<SearchForecastDomainModel, SearchForecastUiModel>
}