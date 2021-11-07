package com.example.remote.sources

import com.example.common.other.Mapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.remote.api.OpenWeatherApi
import com.example.remote.models.current.CurrentForecastNetworkModel
import com.example.data.repositories.RemoteDataSource
import com.example.remote.other.SafeApiRequest
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val mapper: Mapper<CurrentForecastNetworkModel, CurrentForecastDataModel>
): RemoteDataSource {

    override suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): CurrentForecastDataModel {
        val networkCurrentForecast = SafeApiRequest.handleApiRequest {
            api.getCurrentForecast(
                latitude = latitude,
                longitude = longitude,
                units = units
            )
        }
        return mapper.from(networkCurrentForecast)
    }
}