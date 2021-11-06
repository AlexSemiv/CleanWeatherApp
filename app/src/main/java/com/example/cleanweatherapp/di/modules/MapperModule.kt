package com.example.cleanweatherapp.di.modules

import com.example.common.other.Mapper
import com.example.data.data_models.current.MainForecastDataModel
import com.example.data.remote.mappers.MainNetworkDataMapper
import com.example.data.remote.network_models.current.MainForecastNetworkModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun bindsMainForecastNetworkDataMapper(
        mapper: MainNetworkDataMapper
    ): Mapper<MainForecastNetworkModel, MainForecastDataModel>
}