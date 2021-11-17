package com.example.remote.sources

import com.example.common.base.Mapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.data.models.search.SearchForecastDataModel
import com.example.remote.api.OpenWeatherApi
import com.example.remote.models.current.CurrentForecastNetworkModel
import com.example.data.repositories.RemoteDataSource
import com.example.remote.models.search.SearchForecastNetworkModel
import com.example.remote.other.SafeApiRequest
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val currentMapper: Mapper<CurrentForecastNetworkModel, CurrentForecastDataModel>,
    private val searchMapper: Mapper<SearchForecastNetworkModel, SearchForecastDataModel>
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
        return currentMapper.from(networkCurrentForecast)
    }

    override suspend fun getForecastByQuery(
        query: String,
        units: String
    ): SearchForecastDataModel {
        val networkForecastByQuery = SafeApiRequest.handleApiRequest {
            api.searchForecastByQuery(
                query = query,
                units = units
            )
        }
        return searchMapper.from(networkForecastByQuery)
    }
}