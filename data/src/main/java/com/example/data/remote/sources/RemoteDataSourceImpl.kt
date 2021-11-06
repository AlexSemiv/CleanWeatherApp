package com.example.data.remote.sources

import com.example.common.other.Mapper
import com.example.data.data_models.current.MainForecastDataModel
import com.example.data.other.SafeApiRequest
import com.example.data.remote.api.OpenWeatherApi
import com.example.data.remote.network_models.current.MainForecastNetworkModel
import com.example.data.repositories.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val mapper: Mapper<MainForecastNetworkModel, MainForecastDataModel>
): RemoteDataSource {

    override suspend fun getMainForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): MainForecastDataModel {
        val networkMainForecast = SafeApiRequest.handleApiRequest {
            api.getForecast(
                latitude = latitude,
                longitude = longitude,
                units = units
            )
        }
        return mapper.from(networkMainForecast)
    }
}