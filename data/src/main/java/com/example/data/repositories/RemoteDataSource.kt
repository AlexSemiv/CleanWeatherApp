package com.example.data.repositories

import com.example.data.models.current.CurrentForecastDataModel
import com.example.data.models.search.SearchForecastDataModel

interface RemoteDataSource {

    suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): CurrentForecastDataModel

    suspend fun getForecastByQuery(
        query: String,
        units: String
    ) : SearchForecastDataModel
}