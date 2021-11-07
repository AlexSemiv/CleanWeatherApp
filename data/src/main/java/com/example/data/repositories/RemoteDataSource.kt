package com.example.data.repositories

import com.example.data.models.current.CurrentForecastDataModel

interface RemoteDataSource {

    suspend fun getCurrentForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): CurrentForecastDataModel
}