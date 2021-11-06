package com.example.data.repositories

import com.example.data.data_models.current.MainForecastDataModel

interface RemoteDataSource {

    suspend fun getMainForecast(
        latitude: Double,
        longitude: Double,
        units: String
    ): MainForecastDataModel
}